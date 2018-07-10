import React from 'react';

const BeerTable = ({ beers }) => {
    if(!beers) return null;
    console.log(beers);
    const listItems = beers.map(beer => (<li key={beer.id}>{beer.name}</li>));
    return (
    <ul>
{listItems}
    </ul>);
}

export default BeerTable;