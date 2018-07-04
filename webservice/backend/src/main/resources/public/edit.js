import {fetchFromUrl, setupLogger} from './common.js';

export default async function init(errorConsoleSelector) {
    const errorLog = setupLogger(errorConsoleSelector);
    const searchParams = new URLSearchParams(document.location.search);
    const id = searchParams.get('id');
    if (!id) {
        errorLog("Missing parameter: id");
        return false;
    }
    const beer = await fetchFromUrl({path: `/beer/${id}`, errorLog});
}