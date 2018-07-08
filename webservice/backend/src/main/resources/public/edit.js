import {fetchFromUrl, setHeadingAndPageTitleTooBeerName, setupLogger} from './common.js';

function populateForm(beer, errorLog) {
    if (!beer) {
        errorLog('No beer found');
        return false;
    }
    const breweries = fetchFromUrl({path: '/brewery', errorLog});
    const countries = fetchFromUrl({path: '/country', errorLog});
    const getFormElementByName = (name) => document.querySelector(`#main_form [name=${name}]`);
    const setValueByName = (name, beer) => getFormElementByName(name).value = beer[name];
    setValueByName('id', beer);
    setValueByName('name', beer);
    setValueByName('abv', beer);
    setValueByName('ibu', beer);
    setValueByName('kcal', beer);
    setValueByName('webpage', beer);
    const fillSelect = (name, elements, getValue, isSelected) => {
        const select = getFormElementByName(name);
        elements.then((e) => e.forEach(element => {
            const item = document.createElement('option');
            item.setAttribute('value', getValue(element));
            if (isSelected(element)) {
                item.setAttribute('selected', 'selected');
            }
            item.innerHTML = element.name;
            select.appendChild(item);
        }));
    }
    fillSelect('country', countries, country => country.countryCode, country => beer.country && country.countryCode === beer.country.countryCode);
    fillSelect('brewery', breweries, brewery => brewery.id, brewery => beer.brewery && brewery.id === beer.brewery.id);
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
    setHeadingAndPageTitleTooBeerName(beer, '#title_beerName');
    populateForm(beer, errorLog);
}