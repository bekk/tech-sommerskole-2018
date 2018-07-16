export const keys = {
  NAME: 'Name',
  BREWERY: 'Brewery',
  ABV: 'ABV',
  COUNTRY: 'Country',
};

export const getKey = (key) => {
  switch (key) {
    case keys.NAME: return beer => beer.name;
    case keys.ABV: return beer => beer.abv;
    case keys.BREWERY: return beer => (beer.brewery || {}).name;
    case keys.COUNTRY: return beer => (beer.country || {}).name;
    default: throw new Error(`Unknown key: ${key}`);
  }
};
