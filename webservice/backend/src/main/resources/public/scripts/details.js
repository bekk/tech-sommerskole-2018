import {
  fetchFromUrl, insertInNode, setHeadingAndPageTitleTooBeerName, setupLogger, setClassName
} from './common.js';


function writeToOrRemoveInfoNode(selector, content, className) {
  const node = document.querySelector(selector);
  if (!node) {
    return false;
  }
  if (!content) {
    node.remove();
  } else {
    const parent = node.parentNode;
    const infoNode = document.createElement('dd');
    insertInNode(infoNode, content);
    parent.insertBefore(infoNode, node.nextSibling);
    setClassName(infoNode, className);
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

//This function queries Wikipedia.org using a string (beer name) and returns the first article (if any).
async function fetchWikipedia(beer, errorLog) {
  const apiAddress = 'https://en.wikipedia.org/w/api.php';
  const searchUrl = new URL(apiAddress);
  const {searchParams} = searchUrl;
  searchParams.append('action', 'query');
  searchParams.append('list', 'search');
  searchParams.append('utf8', '');
  searchParams.append('format', 'json');
  searchParams.append('origin', '*');
  searchParams.append('srsearch', `beer ${beer.name}`);
  const fetchParams = {
    headers: {
      Accept: 'application/json',
      'Api-User-Agent': 'beer-catalogue1.0',
    },
  };
  const searchResult = await fetchFromUrl({ urlObject: searchUrl, fetchParams, errorLog });
  if (!searchResult.query || !searchResult.query.search) {
    return false;
  }
  const firstHit = searchResult.query.search[0];
  if (!firstHit) {
    return false;
  }
  const { pageid } = firstHit;
  const parseUrl = new URL(apiAddress);
  const parseParams = parseUrl.searchParams;
  parseParams.append('action', 'parse');
  parseParams.append('format', 'json');
  parseParams.append('origin', '*');
  parseParams.append('pageid', pageid);
  const details = await fetchFromUrl({ urlObject: parseUrl, fetchParams, errorLog });
  return (details || {}).parse;
}

function getCountryFlagUrl(country) {
  if (!country) return false;
  let key = country.key;
  if (key === 'en' || key === 'sc' || key === 'wa' || key === 'nd') {
    key = 'gb';
  }
  return `http://www.countryflags.io/${key}/flat/64.png`;
}

//This function updates the html page with data from the server.
function writeInfo(beer) {
  if (!beer) {
    return false;
  }
  writeToOrRemoveInfoNode('#beer_brewery', (beer.brewery || {}).name);
  writeToOrRemoveInfoNode('#beer_country', (beer.country || {}).name);
  writeToOrRemoveInfoNode('#beer_ibu', beer.ibu);
  writeToOrRemoveInfoNode('#beer_abv', beer.abv, 'percentage');
  writeToOrRemoveInfoNode('#beer_kcal', beer.kcal);
  writeToOrRemoveInfoNode('#beer_lastUpdate', beer.updatedAt);
  let webPage;
  if (beer.webpage) {
    webPage = document.createElement('a');
    webPage.setAttribute('href', `http://${beer.webpage}`);
    webPage.setAttribute('target', 'beer_webPage');
    webPage.setAttribute('rel', 'noopener noreferrer');
    webPage.innerText = beer.webpage;
  }
  updateImage('#beer_image', beer.image, beer.title);
  const flagUrl = getCountryFlagUrl(beer.country);
  if (flagUrl) {
    updateImage('#country_flag', flagUrl, `Flag of ${beer.country.name}`);
  }
  writeToOrRemoveInfoNode('#beer_webPage', webPage);
  if (beer.messages) {
    const messageContainer = document.querySelector('messages');
    const ul = document.createElement('ul');
    beer.messages.forEach((msg) => {
      const li = document.createElement('li');
      li.setAttribute('class', 'message_item');
      li.innerHTML = msg;
      ul.appendChild(li);
    });
    messageContainer.appendChild(ul);
  }
}

//This function updates the link url parameter with the id of the current beer
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

//This function updates the html page with the data from the wikipedia query.
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
    (_, relativeUrl) => `href="${new URL(relativeUrl, 'https://en.wikipedia.org')}"`,
  );
  node.innerHTML = textWithQualifiedUrls;
}

//This function is called from the web page
export default async function init(errorConsoleSelector) {
  const errorLog = setupLogger(errorConsoleSelector);
  const searchParams = new URLSearchParams(document.location.search);
  const id = searchParams.get('id');
  if (!id) {
    errorLog('Missing parameter: id');
    return false;
  }
  const beer = await fetchFromUrl({ path: `/beer/${id}`, errorLog });
  setHeadingAndPageTitleTooBeerName(beer, '#title_beerName');
  writeInfo(beer);
  updateEditLink(id);
  await getAndRenderWikiContent(beer, '#wikipedia_content', errorLog);
}
