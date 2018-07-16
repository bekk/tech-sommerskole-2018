import config from 'config';
import { logError } from 'utils/errorBroker';

let beers;

const queryService = async (url) => {
  const response = await fetch(url.href);
  if (!response.ok) {
    logError(`Fetching ${url} returned ${response.status}`);
    if (response.body) {
      const reader = response.body.getReader();
      const decoder = new TextDecoder('utf-8');
      reader.read().then(content => logError(decoder.decode(content.value)));
    }
    return false;
  }
  return response.json();
};

const queryBeers = async () => {
  const url = new URL('beer', config.webSvcBaseUrl);
  url.searchParams.append('limit', 100000);
  return queryService(url);
};

export const refresh = async () => {
  beers = await queryBeers();
  return beers;
};

export const getBeers = async () => beers || refresh().catch(logError);

export const getBeer = async (id) => {
  const url = new URL(`beer/${id}`, config.webSvcBaseUrl);
  return queryService(url);
};
