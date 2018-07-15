import React from 'react';
import BeerTable from 'components/BeerTable';
import { getBeers } from 'apiClient/beerRepository';
import { compareByProperty } from 'utils/arrayUtils';

class BeerListing extends React.Component{
  constructor(props) {
    super(props);
    this.state = {beers: [], sortColumn: null, sortDirection: null };
  }
  sorting({ getKey, column, direction }){
    const orderBy = compareByProperty({ getKey, reverse: direction === 'desc' });
    const beers = this.state.beers.sort(orderBy);
    this.setState({ beers, sortColumn: column, sortDirection: direction });
  }
  componentDidMount(){
    getBeers().then(beers => {
      this.setState({beers});
    });
  }
  render(){
    return (
      <div>
        <BeerTable
          beers={this.state.beers}
          onSort={this.sorting.bind(this)}
          sortDirection={this.state.sortDirection}
          sortColumn={this.state.sortColumn}
        />
      </div>);
  }
}

export default BeerListing;
