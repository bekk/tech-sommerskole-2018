import React from 'react';
import BeerListing from 'components/BeerListing';

const Index = props => (
  <React.Fragment>
    <h1>
      Beer list
    </h1>
    <BeerListing {...props} />
  </React.Fragment>
);

export default Index;
