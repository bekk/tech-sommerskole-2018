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

function writeInfo(beer) {
    if (!beer) return false;
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
    writeToOrRemoveInfoNode('#beer_webPage', webPage);
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
}
