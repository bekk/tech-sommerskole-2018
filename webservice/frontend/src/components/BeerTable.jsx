import React from 'react';
import './beerTable.less';

const buildRow = beer => (
    <tr key={beer.id}>
        <td>{beer.name}</td>
        <td>{(beer.brewery || {}).name}</td>
        <td>{beer.abv}</td>
        <td>{(beer.country || {}).name}</td>
    </tr>
);

const tableHeader = ({ title, onSort, getKey, sortColumn, sortDirection }) => {
  const isSorted = sortColumn === title;
  const direction = isSorted && sortDirection;
  const onClick = () => {
    onSort({
      getKey,
      column: title,
      direction: direction === 'asc' ? 'desc' : 'asc'
    });
  };
  const className = `tableHeaderSortButton sortDirection_${direction}`
  return (
    <th aria-sort={isSorted ? 'sort' : 'none'}>
    <span
      className={className}
      role="button"
      title={`sort by ${title}`}
      onClick={onClick}>
      {title}
    </span>
    </th>
  );
}

const BeerTable = ({ beers, onSort, sortDirection, sortColumn }) => {
    if (!beers) return null;
    const tableRows = beers.map(buildRow);
    return (
        <table>
            <thead>
                <tr>
                    {tableHeader({title: 'Name', onSort, getKey: beer => beer.name, sortColumn, sortDirection })}
                    {tableHeader({title: 'Brewery', onSort, getKey: beer => (beer.brewery || {}).name, sortColumn, sortDirection })}
                    {tableHeader({title: 'ABV', onSort, getKey: beer => beer.abv, sortColumn, sortDirection })}
                    {tableHeader({title: 'Country', onSort, getKey: beer => (beer.country || {}).name, sortColumn, sortDirection })}
                </tr>
            </thead>
            <tbody>
                {tableRows}
            </tbody>
        </table>);
}

export default BeerTable;
