import React from 'react';
import './beerTable.less';

const TableRow = ({ beer }) => (
  <tr>
    <td>
      {beer.name}
    </td>
    <td>
      {(beer.brewery || {}).name}
    </td>
    <td>
      {beer.abv}
    </td>
    <td>
      {(beer.country || {}).name}
    </td>
  </tr>
);

const TableHeader = ({
  title, onSort, getKey, sortColumn, sortDirection,
}) => {
  const isSorted = sortColumn === title;
  const direction = isSorted && sortDirection;
  const onClick = () => {
    onSort({
      getKey,
      column: title,
      direction: direction === 'asc' ? 'desc' : 'asc',
    });
  };
  const className = `tableHeaderSortButton sortDirection_${direction}`;
  return (
    <th aria-sort={isSorted ? 'sort' : 'none'}>
      <span
        className={className}
        role="button"
        title={`sort by ${title}`}
        onClick={onClick}
      >
        {title}
      </span>
    </th>
  );
};

const BeerTable = ({
  beers, onSort, sortDirection, sortColumn,
}) => {
  if (!beers) return null;
  const tableRows = beers.map(beer => <TableRow beer={beer} key={beer.id} />);
  const headerProps = { onSort, sortColumn, sortDirection };
  return (
    <table>
      <thead>
        <tr>
          <TableHeader title="Name" getKey={beer => beer.name} {...headerProps} />
          <TableHeader title="Brewery" getKey={beer => (beer.brewery || {}).name} {...headerProps} />
          <TableHeader title="ABV" getKey={beer => beer.abv} {...headerProps} />
          <TableHeader title="Country" getKey={beer => (beer.country || {}).name} {...headerProps} />
        </tr>
      </thead>
      <tbody>
        {tableRows}
      </tbody>
    </table>);
};

export default BeerTable;
