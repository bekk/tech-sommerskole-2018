import React from 'react';
import BeerDetails from 'components/BeerDetails';
import { getBeer } from 'apiClient/beerRepository';

class Details extends React.Component {
  constructor(props) {
    super(props);
    this.state = { };
  }

  componentDidMount() {
    const { match } = this.props;
    getBeer(match.params.id)
      .then(beer => this.setState({ beer }));
  }

  render() {
    const { beer } = this.state;
    if (!beer) return null;
    return (
      <React.Fragment>
        <h1>
        Details
        </h1>
        <BeerDetails beer={beer} />
      </React.Fragment>
    );
  }
}

export default Details;
