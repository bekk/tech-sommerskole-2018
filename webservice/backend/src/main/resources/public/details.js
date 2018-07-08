import {fetchFromUrl, insertInNode, setHeadingAndPageTitleTooBeerName, setupLogger} from './common.js';

function writeToOrRemoveInfoNode(selector, content) {
    const node = document.querySelector(selector);
    if (!node) {
        return false;
    }
    if (!content) {
        node.remove();
    } else {
        const infoNode = document.querySelector(`${selector} .beer_info`);
        insertInNode(infoNode, content);
    }
}

function updateImage(selector, src, alt) {
    const image = document.querySelector(selector);
    if (!image) {
        return false;
    }
    if (!src) {
        image.remove();
    }
    image.setAttribute('src', src);
    image.setAttribute('alt', alt);
    image.removeAttribute('hidden');
    return image;
}

async function fetchWikipedia(beer, errorLog) {
    const apiAddress = 'https://en.wikipedia.org/w/api.php';
    const searchUrl = new URL(apiAddress);
    const searchParams = searchUrl.searchParams;
    searchParams.append('action', 'query');
    searchParams.append('list', 'search');
    searchParams.append('utf8', '');
    searchParams.append('format', 'json');
    searchParams.append('origin', '*');
    searchParams.append('srsearch', `beer ${beer.name}`);
    const fetchParams = {
        headers: {
            Accept: 'application/json',
            'Api-User-Agent': 'beer-catalogue1.0'
        }
    };
    const searchResult = await fetchFromUrl({urlObject: searchUrl, fetchParams, errorLog});
    if (!searchResult.query || !searchResult.query.search) {
        return false;
    }
    const firstHit = searchResult.query.search[0];
    const {pageid} = firstHit;
    const parseUrl = new URL(apiAddress);
    const parseParams = parseUrl.searchParams;
    parseParams.append('action', 'parse');
    parseParams.append('format', 'json');
    parseParams.append('origin', '*');
    parseParams.append('pageid', pageid);
    const details = await fetchFromUrl({urlObject: parseUrl, fetchParams, errorLog});
    return (details || {}).parse;
}

function writeInfo(beer) {
    if (!beer) {
        return false;
    }
    writeToOrRemoveInfoNode('#beer_brewery', (beer.brewery || {}).name);
    writeToOrRemoveInfoNode('#beer_country', (beer.country || {}).name);
    writeToOrRemoveInfoNode('#beer_ibu', beer.ibu);
    writeToOrRemoveInfoNode('#beer_abv', beer.abv);
    writeToOrRemoveInfoNode('#beer_kcal', beer.kcal);
    writeToOrRemoveInfoNode('#beer_lastUpdate', beer.updatedAt);
    let webPage;
    if (beer.webpage) {
        webPage = document.createElement('a');
        webPage.setAttribute('href', `http://${beer.webpage}`);
        webPage.setAttribute('target', `beer_webPage`);
        webPage.setAttribute('rel', `noopener noreferrer`);
        webPage.innerText = beer.webpage;
    }
    updateImage('#beer_image', beer.image, beer.title);
    const flagUrl = beer.country && `http://www.countryflags.io/${beer.country.key}/flat/64.png`;
    updateImage('#country_flag', flagUrl, `Flag of ${beer.country.name}`);
    writeToOrRemoveInfoNode('#beer_webPage', webPage);
}

function updateEditLink(id) {
    const link = document.querySelector('#link_edit');
    if (!link) {
        return false;
    }
    const url = new URL(link.getAttribute('href'), document.location.href);
    url.searchParams.append('id', id);
    link.setAttribute('href', url.href);
    link.removeAttribute('hidden');
}

async function getAndRenderWikiContent(beer, selector, errorLog) {
    const node = document.querySelector(selector);
    if (!node) {
        return false;
    }
    const content = await fetchWikipedia(beer, errorLog);
    if (!content) {
        node.remove();
        return false;
    }
    const contentText = content.text['*'];
    const replacePattern = /href="(\/[^"]+)"/g;
    const textWithQualifiedUrls = contentText.replace(
        replacePattern,
        (_, relativeUrl) => `href="${new URL(relativeUrl, 'https://en.wikipedia.org')}"`);
    node.innerHTML = textWithQualifiedUrls;
}

export default async function init(errorConsoleSelector) {
    const errorLog = setupLogger(errorConsoleSelector);
    const searchParams = new URLSearchParams(document.location.search);
    const id = searchParams.get('id');
    if (!id) {
        errorLog("Missing parameter: id");
        return false;
    }
    const beer = await fetchFromUrl({path: `/beer/${id}`, errorLog});
    setHeadingAndPageTitleTooBeerName(beer, '#title_beerName');
    writeInfo(beer);
    updateEditLink(id);
    await getAndRenderWikiContent(beer, "#wikipedia_content", errorLog);
}
