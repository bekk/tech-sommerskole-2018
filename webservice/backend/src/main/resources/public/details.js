import {fetchFromUrl, insertInNode, setupLogger} from './common.js';

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

function writeInfo(beer) {
    if (!beer) {
        return false;
    }
    const title = document.querySelector('#title_beerName');
    title.innerText = document.title = beer.name;
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

export default async function init(errorConsoleSelector) {
    const errorLog = setupLogger(errorConsoleSelector);
    const searchParams = new URLSearchParams(document.location.search);
    const id = searchParams.get('id');
    if (!id) {
        errorLog("Missing parameter: id");
        return false;
    }
    const beer = await fetchFromUrl({path: `/beer/${id}`, errorLog});

    writeInfo(beer);
    updateEditLink(id);
}
