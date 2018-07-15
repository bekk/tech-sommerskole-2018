import React from 'react';
import BeerTable from 'components/BeerTable';
import { getBeers } from 'apiClient/beerRepository';
import { compareByKey } from 'utils/arrayUtils';

class BeerListing extends React.Component {
  constructor(props) {
    super(props);
    this.state = { beers: [], sortColumn: null, sortDirection: null };
    this.sorting = this.sorting.bind(this);
  }

  componentDidMount() {
    getBeers().then((beers) => {
      this.setState({ beers });
    });
  }

  sorting({ getKey, column, direction }) {
    const orderBy = compareByKey({ getKey, reverse: direction === 'desc' });
    const { beers } = this.state;
    beers.sort(orderBy);
    this.setState({ beers, sortColumn: column, sortDirection: direction });
  }

  render() {
    const { beers, sortDirection, sortColumn } = this.state;
    return (
      <div>
        <BeerTable
          beers={beers}
          onSort={this.sorting}
          sortDirection={sortDirection}
          sortColumn={sortColumn}
        />
      </div>);
  }
}

export default BeerListing;
