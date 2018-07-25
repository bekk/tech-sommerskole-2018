import common from './common';

const config = {
  webSvcBaseUrl: 'http://localhost:8080',
  rateBeerUrl: 'https://api.r8.beer/v1/api/graphql/',
};

export default Object.assign(common, config);
