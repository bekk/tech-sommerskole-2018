import React from 'react';
import { searchByName } from 'apiClient/rateBeerClient';

class RateBeerInfo extends React.Component {
  componentDidMount() {
    this.fetchData();
  }

  componentDidUpdate(prevProps) {
    const { name } = this.props;
    if (prevProps.name !== name) {
      this.fetchData();
    }
  }

  fetchData() {
    const { name } = this.props;
    searchByName(name)
      .then((searchResult) => {
        if (searchResult) {
          this.setState({ searchResult });
        }
      });
  }

  render() {
    const { searchResult } = this.state || {};
    return null;
  }
}

export default RateBeerInfo;
