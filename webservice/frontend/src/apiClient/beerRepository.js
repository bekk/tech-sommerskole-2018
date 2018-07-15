import config from 'config';
import { logError } from 'utils/errorBroker';

const queryService = async function () {
  const url = new URL('beer', config.webSvcBaseUrl);
  url.searchParams.append('limit', 100000);
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

export const getBeers = queryService;
