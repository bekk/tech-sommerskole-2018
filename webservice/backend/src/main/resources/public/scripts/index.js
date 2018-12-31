import { fetchFromUrl, insertInNode, setupLogger, setClassName } from './common.js'; //Import functions from another file.

//This function uses sessionstorage (if supported by browser) too persist filter and sorting data between page loads
function getOrSetQueryStateFromSessionStorage(errorLog, state) {
  const storagekey = 'beer_table_query_state';
  if (!sessionStorage) return false;
  if (state) {
    sessionStorage.setItem(storagekey, JSON.stringify(state));
    return state;
  } else {
    try {
      const stored = sessionStorage.getItem(storagekey);
      return stored && JSON.parse(stored);
    } catch (error) {
      errorLog(error);
      return false;
    }
  }
}

//This function builds a function to be called to query the server for data.
function setUpBeerFetcher({ errorLog }) {
  const path = '/beer';
  let queryState = getOrSetQueryStateFromSessionStorage(errorLog) || {};
  return function (query) {
    if (query && typeof query === 'boolean') {
      queryState = {};
    } else {
      const newQueryParams = query || {};
      const sortOrder = { sortDescending: undefined };
      if (queryState.sortType && queryState.sortType === newQueryParams.sortType) {
        if (!queryState.sortDescending) {
          sortOrder.sortDescending = true;
        }
      }
      queryState = Object.assign(queryState, newQueryParams, sortOrder);
      getOrSetQueryStateFromSessionStorage(errorLog, queryState);
    }
    return fetchFromUrl({ path, errorLog, params: queryState })
      .then(beers => ({ beers, query: queryState }));
  };
}

//This function builds a function to create the rows and columns in the table from beer data.
export function setupTableRenderer({ tableSelector, sorter }) {
  const table = document.querySelector(tableSelector);

  return function (beers, query) {
    //Start by clearing the content of the existing table
    table.innerHTML = '';
    if (!beers) {
      //If there are no data, just return.
      return;
    }
    const buildElement = (parent, className, tag) => {
      //This is a utility function to create elements, used below.
      //HTML-elements are created with a function on document, before they are inserted in the DOM.
      const element = document.createElement(tag);
      if (className) {
        setClassName(element, className);
      }
      if (parent) {
        parent.appendChild(element);
      }
      return element;
    };
    const buildCell = (content, className, tag = 'td') => {
      const element = buildElement(null, className, tag);
      //An imported function is used to append element as a child to another node.
      return insertInNode(element, content);
    };
    const colGroup = buildElement(table, null, 'colgroup');
    const headerRow = buildElement(table, null, 'tr');

    //Build headers and columns
    const headers = [
      ['Name', 'BEER_NAME', 'column_wide'],
      ['Brewery', 'BREWERY_NAME', 'column_wide'],
      ['ABV', 'ABV', 'column_narrow'],
      ['Country', 'COUNTRY', 'column_medium'],
    ];
    headers.forEach((hdr) => {
      const title = hdr[0];
      const sortKey = hdr[1];
      const className = hdr[2];
      const link = document.createElement('span');
      link.setAttribute('role', 'button');
      link.setAttribute('title', `Order by ${title}.`);
      link.innerText = title;
      link.onclick = () => sorter({ sortType: sortKey });
      if (query && query.sortType === sortKey) {
        setClassName(link, query.sortDescending ? 'order_by_desc' : 'order_by_asc');
      }
      const cell = buildCell(link, null, 'th');
      headerRow.appendChild(cell);
      const col = buildElement(colGroup, className, 'col');
    });

    //Insert one row per beer
    beers.forEach((beer) => {
      const brewery = beer.brewery || {};
      const country = beer.country || {};
      const row = document.createElement('tr');
      const detailsLinkHref = `/details.html?id=${beer.id}`;
      const nameWithLink = document.createElement('a');
      nameWithLink.setAttribute('href', detailsLinkHref);
      nameWithLink.innerHTML = beer.name;
      row.appendChild(buildCell(nameWithLink));
      row.appendChild(buildCell(brewery.name));
      row.appendChild(buildCell(beer.abv, 'percentage'));
      row.appendChild(buildCell(country.name));
      row.setAttribute('role', 'link');
      row.setAttribute('href', detailsLinkHref);
      row.onclick = () => window.location = detailsLinkHref;
      table.appendChild(row);
    });
  };
}

//This function builds a function to filter beer by the columns
export async function setupCountryFilter(props) {

  //Destructuring of the props-parameter to get the inner properties
  const {
    selector, errorLog, refreshBeers, path,
  } = props;

  //Get the html element
  const select = document.querySelector(selector);

  if (!select) {
    return;
  }

  const countries = await fetchFromUrl({ path, errorLog });
  const savedState = getOrSetQueryStateFromSessionStorage(errorLog);

  const groupBy = (items, category) => items.reduce((acc, i) => {
    (acc[i[category]] = acc[i[category]] || []).push(i);
    return acc;
  }, {});

  select.innerHTML = '';

  const countriesWithBeers = countries.filter(c => typeof c.numberOfBeers === 'undefined' || c.numberOfBeers > 0);

  Object.entries(groupBy(countriesWithBeers, 'continent')).forEach((continent) => {
    const group = document.createElement('optgroup');
    group.setAttribute('label', continent[0]);
    continent[1].forEach((country) => {
      const item = document.createElement('option');
      item.setAttribute('value', country.countryCode);
      item.innerHTML = country.name;
      if (savedState && savedState.countries && savedState.countries.includes(country.countryCode)) {
        item.setAttribute('selected', 'selected');
      }
      group.appendChild(item);
    });
    select.appendChild(group);
  });

  const getSelectedItems = () => {
    const result = [];
    const options = select && select.options;
    for (let i = 0; i < options.length; i++) {
      const opt = options[i];

      if (opt.selected) {
        result.push(opt.value || opt.text);
      }
    }
    return result;
  };
  select.onchange = () => refreshBeers({ countries: getSelectedItems().join(',') });
  return getSelectedItems;
}

//This function arms the reset link on the page to reset all filters (form fields)
export function setupResetLink(linkSelector, formSelector, refresh, errorLog) {
  const resetLink = document.querySelector(linkSelector);
  const form = document.querySelector(formSelector);
  resetLink.onclick = () => Promise.resolve(refresh(true))
    .then(() => form.reset())
    .then(() => getOrSetQueryStateFromSessionStorage(errorLog, {}));
}

//This function enables all the filter elements
function setUpFilters(props) {
  const savedState = getOrSetQueryStateFromSessionStorage(props.errorLog);
  const setupTextBoxFilter = (selector, name) => {
    const box = document.querySelector(selector);
    if (!box) {
      return;
    }
    if (savedState && savedState[name]) {
      box.value = savedState[name];
    }
    box.onchange = event => props.refreshBeers({ [name]: event.target.value });
  };
  setupTextBoxFilter('#filter_abv_min', 'minAbv');
  setupTextBoxFilter('#filter_abv_max', 'maxAbv');
  setupTextBoxFilter('#filter_limit', 'limit');
  setupResetLink('#filter_reset', '#filter_form', props.refreshBeers, props.errorLog);
  return setupCountryFilter({ selector: '#filter_country', path: '/country', ...props });
}

// This function is called from the web page, referencing all the functions above.
export default async function init(mainTableSelector, errorConsoleSelector) {
  let refreshBeers;
  const errorLog = setupLogger(errorConsoleSelector);
  const getBeers = setUpBeerFetcher({ errorLog });
  const renderBeers = setupTableRenderer({
    tableSelector: mainTableSelector,
    sorter: sortParams => refreshBeers(sortParams),
  });
  refreshBeers = query => getBeers(query).then(({ beers, query }) => renderBeers(beers, query));
  refreshBeers();
  await setUpFilters({ errorLog, refreshBeers });
}
