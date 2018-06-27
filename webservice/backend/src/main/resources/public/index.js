function setupLogger(selector) {
    const error_console = document.querySelector(selector);
    return txt => {
        if (error_console) {
            const p = document.createElement('p');
            p.innerText = txt;
            error_console.insertBefore(p, error_console.firstChild);
        }
        console.error(txt);
    };
}

function fetchFromUrl({path, errorLog, params = {}}) {
    const url = new URL(path, document.location.href);
    Object.entries(params)
        .filter(entry => typeof entry[1] !== 'undefined' && entry[1] !== '')
        .forEach(entry => {
            url.searchParams.append(entry[0], entry[1]);
        });
    return fetch(url.href)
        .then(response => {
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

function setUpFetcher({errorLog}) {
    const path = '/beer';
    let searchState = {};
    return function (params) {
        const {searchParams} = params || {};
        searchState = Object.assign(searchState, searchParams);
        return fetchFromUrl({path, errorLog, params: searchState});
    }
}

function setupRenderer(tableSelector) {
    const table = document.querySelector(tableSelector);
    return function (beers) {
        if (!beers) return;
        table.innerHTML = '';
        const headerRow = document.createElement("tr");
        const buildCell = (txt, tag = 'td') => {
            const node = document.createElement(tag);
            node.innerText = txt || '';
            return node;
        };
        ['Name', 'Brewery', 'ABV', 'Country'].forEach(hdr => {
            headerRow.appendChild(buildCell(hdr, 'th'));
        });
        table.appendChild(headerRow);
        beers.forEach(beer => {
            const brewery = beer.brewery || {};
            const country = beer.country || {};
            const row = document.createElement('tr');
            row.appendChild(buildCell(beer.name));
            row.appendChild(buildCell(brewery.name));
            row.appendChild(buildCell(beer.abv));
            row.appendChild(buildCell(country.name));
            table.appendChild(row);
        })
    }
}

async function setUpCountryFilter(props) {
    const {errorLog, getBeers, renderBeers} = props;
    const path = "/country";
    const countries = await fetchFromUrl({path, errorLog});
    const select = document.querySelector('#filter_country');
    select.innerHTML = '';
    countries.forEach(country => {
        const item = document.createElement('option');
        item.setAttribute('value', country.countryCode);
        item.innerHTML = country.name;
        select.appendChild(item);
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
    select.onchange = () => {
        getBeers({searchParams: {countries: getSelectedItems().join(',')}})
            .then(beers => renderBeers(beers));
    }
}

function setUpFilters(props) {
    const {renderBeers, getBeers} = props;
    const setupTextBoxFilter = (selector, name) => {
        const box = document.querySelector(selector);
        box.onchange = event => {
            getBeers({searchParams: {[name]: event.target.value}})
                .then(beers => renderBeers(beers));
        }
    };
    setupTextBoxFilter('#filter_abv_min', 'minAbv');
    setupTextBoxFilter('#filter_abv_max', 'maxAbv');
    setupTextBoxFilter('#filter_limit', 'limit');
    return setUpCountryFilter(props);
}

async function init(mainTableSelector, errorConsoleSelector) {
    const errorLog = setupLogger(errorConsoleSelector);
    const getBeers = setUpFetcher({errorLog});
    const renderBeers = setupRenderer(mainTableSelector);
    const beers = await getBeers();
    renderBeers(beers);
    await setUpFilters({errorLog, renderBeers, getBeers});
}
