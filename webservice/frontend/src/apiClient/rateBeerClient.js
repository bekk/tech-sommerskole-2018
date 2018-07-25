import config from 'config';
import { logError } from 'utils/errorBroker';

const queryCache = {};

const buildQuery = (name) => {
  const query = `query { beerSearch(query:"${name}") { items { id, name, description, imageUrl, calories, overallScore, brewer {id, name, web}}}}`;
  return { query };
};

const postQuery = async (name) => {
  const { rateBeerApiKey, rateBeerUrl } = config;
  if (!(rateBeerApiKey && name)) return false;
  if (queryCache[name]) return queryCache[name];
  const response = await fetch(rateBeerUrl, {
    method: 'POST',
    headers: {
      Accept: 'application/json',
      'x-api-key': rateBeerApiKey,
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(buildQuery(name)),
  });
  if (!response.ok) {
    logError(`Searching ${rateBeerUrl} for ${name} returned ${response.status}`);
    if (response.body) {
      const reader = response.body.getReader();
      const decoder = new TextDecoder('utf-8');
      reader.read()
        .then(content => logError(decoder.decode(content.value)));
    }
    return false;
  }
  const result = response.json();
  queryCache[name] = result;
  return result;
};

export const searchByName = name => postQuery(name)
  .catch(logError);
