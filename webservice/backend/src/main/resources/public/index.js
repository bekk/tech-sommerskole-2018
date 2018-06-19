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
            throw new Error(`Fetching ${url} returned ${response.status}`);
        }
        return response.json();
    }
    catch (e) {
        props.errorLog(e);
    }
}

async function init(mainTableSelector, errorConsoleSelector) {
    const errorLog = createLogger(errorConsoleSelector);
    const beers = await fetchBeers({errorLog});
    if (beers) {
        const table = document.querySelector(mainTableSelector);
        table.innerText = beers;
    }
}

