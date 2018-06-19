function setupLogger(selector) {
    const error_console = document.querySelector(selector);
    return txt => {
        if (error_console) {
            error_console.innerHTML = txt;
        }
        console.error(txt);
    };
}

function setUpFetcher({errorLog}) {
    const path = '/beer';
    let searchState = {};
    return function (params) {
        const {searchParams} = params || {};
        const url = new URL(path, document.location.href);
        searchState = Object.assign(searchState, searchParams);
        Object.entries(searchState)
            .filter(entry => typeof entry[1] !== 'undefined' && entry[1] !== '')
            .forEach(entry => {
                url.searchParams.append(entry[0], entry[1]);
            });
        return fetch(url.href)
            .then(response => {
                if (!response.ok) {
                    errorLog(`Fetching ${url} returned ${response.status}`);
                    return false;
                }
                return response.json();
            })
            .catch(error => errorLog(error));
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
}

async function init(mainTableSelector, errorConsoleSelector) {
    const errorLog = setupLogger(errorConsoleSelector);
    const getBeers = setUpFetcher({errorLog});
    const renderBeers = setupRenderer(mainTableSelector);
    const beers = await getBeers();
    renderBeers(beers);
    setUpFilters({errorLog, renderBeers, getBeers});
}
