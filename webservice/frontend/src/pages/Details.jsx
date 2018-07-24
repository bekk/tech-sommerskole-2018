import React from 'react';
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
        /*Details here...*/
      </React.Fragment>
    );
  }
}

export default Details;
