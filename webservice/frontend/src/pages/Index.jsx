import React from 'react';
import BeerTable from 'components/BeerTable';
import { getBeers } from 'apiClient/beerRepository';

class Index extends React.Component{
    constructor(props) {
        super(props);
        this.state = {beers: []};
    }
    componentDidMount(){
        getBeers().then(beers => {
            this.setState({beers});
        });
    }
    render(){
        return (
        <div>
            <BeerTable beers={this.state.beers} />
        </div>);
    }
}
export default Index;
