import {fetchFromUrl, setupLogger} from './common.js';
import {setupCountryFilter, setupResetLink, setupTableRenderer} from './index.js';


function setUpBeerFetcher({errorLog}) {
    const path = '/beer';
    return function ({payload}) {
        return fetchFromUrl({path, errorLog, params: payload});
    };
}

async function setupFilters(props) {
    const {renderBeers, formSelector, errorLog} = props;
    setupResetLink('#filter_reset', formSelector, () => renderBeers());
    const getSelectedCountries = await setupCountryFilter({
        selector: `${formSelector} select[name=country_desired]`,
        errorLog,
        path: '/country',
        refreshBeers: () => fetchRecommendation()
    });
    const inputs = Object.entries({
        abvWeight: 'abv_importance',
        abvValue: 'abv_desired',
        kcalWeight: 'kcal_importance',
        kcalValue: 'kcal_desired',
        cityWeight: 'city_importance',
        cityValue: 'city_desired',
        countryWeight: 'country_importance'
    }).map(([key, value]) => ({
        key, formControl: document.querySelector(`${formSelector} input[name=${value}]`)
    }));

    const fetchRecommendation = () => {
        const payload = inputs.reduce((o, p) => {
            o[p.key] = p.formControl.value;
            return o;
        }, {});
        payload.countryValue = getSelectedCountries();
        props.getBeers({payload}).then(renderBeers);
    };

    inputs.forEach(i => i.formControl.onchange = fetchRecommendation);
}

export default async function init(mainTableSelector, formSelector, errorConsoleSelector) {
    const errorLog = setupLogger(errorConsoleSelector);
    const getBeers = setUpBeerFetcher({errorLog});
    const renderBeers = setupTableRenderer({tableSelector: mainTableSelector});
    await setupFilters({renderBeers, getBeers, formSelector, errorLog});
}
