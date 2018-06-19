function createLogger(selector){
    const error_console = document.querySelector(selector);
    return txt => {
        if (error_console) {
            error_console.innerHTML = txt;
        }
        console.error(txt);
    };
}

async function fetchBeers(props){
    const url = '/beer';
    try {
        const response = await fetch(url);
        if(!response.ok){
            props.error(`Fetching ${url} returned ${response.status}`);
            return;
        }
        return response.json();
    }
    catch (e) {
        props.errorLog(e);
    }
}

function renderBeerTable(tableSelector, beers) {
    if (!beers) return;
    const table = document.querySelector(tableSelector);
    table.innerHTML = '';
    const headerRow = document.createElement("tr");
    const buildCell = (txt, tag = 'td') => {
        const node = document.createElement(tag);
        node.innerText = txt || '';
        return node;
    };
    ['Name', 'Brewery', 'ABV', 'Independent', 'Country'].forEach(hdr => {
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
        row.appendChild(buildCell(beer.independent ? 'Independent' : 'Corporate'));
        row.appendChild(buildCell(country.name));
        table.appendChild(row);
    })
}

async function init(mainTableSelector, errorConsoleSelector) {
    const errorLog = createLogger(errorConsoleSelector);
    const beers = await fetchBeers({errorLog});
    renderBeerTable(mainTableSelector, beers);
}

