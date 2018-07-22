import React from 'react';
import { Link } from 'react-router-dom';
import { getKey, keys } from 'utils/beerUtils';
import '../styles/beerTable.less';

const TableHeader = ({
  column, onSort, sortColumn, sortDirection,
}) => {
  const isSorted = sortColumn === column;
  const direction = isSorted && sortDirection;
  const onClick = () => {
    onSort({
      column,
      direction: direction === 'asc' ? 'desc' : 'asc',
    });
  };
  const className = `tableHeaderSortButton sortDirection_${direction}`;
  return (
    <th aria-sort={isSorted ? 'sort' : 'none'}>
      <span
        className={className}
        role="button"
        title={`sort by ${column}`}
        onClick={onClick}
      >
        {column}
      </span>
    </th>
  );
};

const TableRow = ({ beer }) => (
  <tr>
    <td>
      <Link to={`/beer/${beer.id}`}>
        {getKey(keys.NAME)(beer)}
      </Link>
    </td>
    <td>
      {getKey(keys.BREWERY)(beer)}
    </td>
    <td>
      {getKey(keys.ABV)(beer)}
    </td>
    <td>
      {getKey(keys.COUNTRY)(beer)}
    </td>
  </tr>
);

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
          <TableHeader column={keys.NAME} {...headerProps} />
          <TableHeader column={keys.BREWERY} {...headerProps} />
          <TableHeader column={keys.ABV} {...headerProps} />
          <TableHeader column={keys.COUNTRY} {...headerProps} />
        </tr>
      </thead>
      <tbody>
        {tableRows}
      </tbody>
    </table>);
};

export default BeerTable;
