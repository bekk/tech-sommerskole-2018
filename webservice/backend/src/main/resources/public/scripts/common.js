export function setupLogger(selector) {
  const error_console = document.querySelector(selector);
  return function (txt) {
    if (error_console) {
      const p = document.createElement('p');
      p.innerText = txt;
      error_console.insertBefore(p, error_console.firstChild);
    }
    console.error(txt);
  };
}


export function fetchFromUrl({
  path, errorLog, params, urlObject, fetchParams,
}) {
  const url = urlObject || new URL(path, document.location.href);
  Object.entries(params || {})
    .filter(entry => typeof entry[1] !== 'undefined' && entry[1] !== '')
    .forEach((entry) => {
      url.searchParams.append(entry[0], entry[1]);
    });
  return fetch(url.href, fetchParams)
    .then((response) => {
      if (!response.ok) {
        errorLog(`Fetching ${url} returned ${response.status}`);
        if (response.body) {
          const reader = response.body.getReader();
          const decoder = new TextDecoder('utf-8');
          reader.read().then(content => errorLog(decoder.decode(content.value)));
        }
        return false;
      }
      return response.json();
    })
    .catch(error => errorLog(error));
}

export function insertInNode(node, content) {
  const contentType = typeof content;
  switch (contentType) {
    case 'string':
    case 'number':
      node.innerText = content;
      break;
    case 'undefined':
      node.innerText = '';
      break;
    default:
      node.appendChild(content);
      break;
  }
  return node;
}

export function setHeadingAndPageTitleTooBeerName(beer, headingSelector = '#title_beerName') {
  if (!beer) {
    return false;
  }
  const heading = document.querySelector(headingSelector);
  if (heading) {
    heading.innerText = beer.name;
  }
  document.title = beer.name;
}
