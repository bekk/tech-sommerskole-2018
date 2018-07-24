import React from 'react';
import BeerTable from 'components/BeerTable';
import TextFilter from 'components/TextFilter';
import { getBeers } from 'apiClient/beerRepository';
import { compareByKey } from 'utils/arrayUtils';
import { getKey, keys } from 'utils/beerUtils';

class BeerCatalogue extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      beers: [],
      sortColumn: null,
      sortDirection: null,
    };
    this.sorting = this.sorting.bind(this);
    this.textFiltering = this.textFiltering.bind(this);
  }

  componentDidMount() {
    getBeers().then((beers) => {
      this.setState({
        beers
      });
    });
  }

  getAllBeers() {
    return getBeers();
  }

  sorting({ column, direction }) {
    const orderBy = compareByKey({ getKey: getKey(column), reverse: direction === 'desc' });
    const { beers } = this.state;
    beers.sort(orderBy);
    this.setState({ beers, sortColumn: column, sortDirection: direction });
  }

  textFiltering({ key, value }) {
    const { sortColumn, sortDirection } = this.state;
    let beers;
    if (!value) {
      beers = this.getAllBeers();
    } else {
      const pattern = new RegExp(value);
      const getThisKey = getKey(key);
      beers = this.getAllBeers()
        .then(all => all.filter(b => getThisKey(b)
          .match(pattern)));
    }
    if (sortColumn) {
      const orderBy = compareByKey({ getKey: getKey(sortColumn), reverse: sortDirection === 'desc' });
      beers = beers.then(unsorted => unsorted.sort(orderBy));
    }
    beers.then(sortedAndFiltered => this.setState({ beers: sortedAndFiltered }));
  }

  render() {
    const { beers, sortDirection, sortColumn } = this.state;
    return (
      <div>
        <div className="filter_group">
          <TextFilter id="filter_name" property={keys.NAME} onFilterChange={this.textFiltering} />
        </div>
        <BeerTable
          beers={beers}
          onSort={this.sorting}
          sortDirection={sortDirection}
          sortColumn={sortColumn}
        />
      </div>);
  }
}

export default BeerCatalogue;
