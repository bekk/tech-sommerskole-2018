import {fetchFromUrl, insertInNode, setupLogger} from './common.js';


function setUpBeerFetcher({errorLog}) {
    const path = '/beer';
    let queryState = {};
    return function (query) {
        if (query && typeof query === 'boolean') {
            queryState = {};
        } else {
            const newQueryParams = query || {};
            const sortOrder = {sortDescending: undefined};
            if (queryState.sortType && queryState.sortType === newQueryParams.sortType) {
                if (!queryState.sortDescending) {
                    sortOrder.sortDescending = true;
                }
            }
            queryState = Object.assign(queryState, newQueryParams, sortOrder);
        }
        return fetchFromUrl({path, errorLog, params: queryState});
    };
}

export function setupTableRenderer({tableSelector, sorter}) {
    const table = document.querySelector(tableSelector);
    return function (beers) {
        table.innerHTML = '';
        if (!beers) {
            return;
        }
        const colGroup = document.createElement('colgroup');
        table.appendChild(colGroup);
        const headerRow = document.createElement('tr');
        const buildCell = (content, tag = 'td') => insertInNode(document.createElement(tag), content);
        const headers = [
            ['Name', 'BEER_NAME', 'column_wide'],
            ['Brewery', 'BREWERY_NAME', 'column_wide'],
            ['ABV', 'ABV', 'column_narrow'],
            ['Country', 'COUNTRY', 'column_medium']
        ];
        headers.forEach(function (hdr) {
            const title = hdr[0];
            const sortKey = hdr[1];
            const className = hdr[2];
            const link = document.createElement('span');
            link.setAttribute('role', 'button');
            link.setAttribute('title', `Order by ${title}.`);
            link.innerText = title;
            link.onclick = () => sorter({sortType: sortKey});
            const cell = buildCell(link, 'th');
            headerRow.appendChild(cell);
            const col = document.createElement('col');
            col.setAttribute('class', className);
            colGroup.appendChild(col);
        });
        table.appendChild(headerRow);
        beers.forEach(function (beer) {
            const brewery = beer.brewery || {};
            const country = beer.country || {};
            const row = document.createElement('tr');
            const detailsLink = document.createElement('a');
            const detailsLinkHref = `/details.html?id=${beer.id}`;
            detailsLink.setAttribute('href', detailsLinkHref);
            detailsLink.innerText = beer.name;
            row.appendChild(buildCell(detailsLink));
            row.appendChild(buildCell(brewery.name));
            row.appendChild(buildCell(beer.abv));
            row.appendChild(buildCell(country.name));
            row.setAttribute('role', 'link');
            row.onclick = () => window.open(detailsLinkHref);
            table.appendChild(row);
        });
    };
}

export async function setupCountryFilter(props) {
    const {selector, errorLog, refreshBeers, path} = props;
    const select = document.querySelector(selector);
    if (!select) {
        return;
    }
    const countries = await fetchFromUrl({path, errorLog});
    const groupBy = (items, category) => items.reduce(function (acc, i) {
        (acc[i[category]] = acc[i[category]] || []).push(i);
        return acc;
    }, {});
    select.innerHTML = '';
    const countriesWithBeers = countries.filter(c => typeof c.numberOfBeers === 'undefined' || c.numberOfBeers > 0);
    Object.entries(groupBy(countriesWithBeers, 'continent')).forEach(continent => {
        const group = document.createElement('optgroup');
        group.setAttribute('label', continent[0]);
        continent[1].forEach(country => {
            const item = document.createElement('option');
            item.setAttribute('value', country.countryCode);
            item.innerHTML = country.name;
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
    select.onchange = () => refreshBeers({countries: getSelectedItems().join(',')});
    return getSelectedItems;
}

export function setupResetLink(linkSelector, formSelector, refresh) {
    const resetLink = document.querySelector(linkSelector);
    const form = document.querySelector(formSelector);
    resetLink.onclick = () => Promise.resolve(refresh(true)).then(() => form.reset());
}

function setUpFilters(props) {
    const setupTextBoxFilter = (selector, name) => {
        const box = document.querySelector(selector);
        if (!box) {
            return;
        }
        box.onchange = event => props.refreshBeers({[name]: event.target.value});
    };
    setupTextBoxFilter('#filter_abv_min', 'minAbv');
    setupTextBoxFilter('#filter_abv_max', 'maxAbv');
    setupTextBoxFilter('#filter_limit', 'limit');
    setupResetLink('#filter_reset', '#filter_form', props.refreshBeers);
    return setupCountryFilter({selector: '#filter_country', path: '/country', ...props});
}

export default async function init(mainTableSelector, errorConsoleSelector) {
    let refreshBeers;
    const errorLog = setupLogger(errorConsoleSelector);
    const getBeers = setUpBeerFetcher({errorLog});
    const renderBeers = setupTableRenderer({
        tableSelector: mainTableSelector,
        sorter: (sortParams) => refreshBeers(sortParams)
    });
    refreshBeers = (query) => getBeers(query).then(beers => renderBeers(beers));
    refreshBeers(true);
    await setUpFilters({errorLog, refreshBeers});
}
