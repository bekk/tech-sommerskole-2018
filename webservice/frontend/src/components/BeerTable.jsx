import React from 'react';
import { keys, getKey } from 'utils/beerUtils';
import './beerTable.less';

const TableHeader = ({
  property, onSort, sortColumn, sortDirection,
}) => {
  const isSorted = sortColumn === property;
  const direction = isSorted && sortDirection;
  const onClick = () => {
    onSort({
      column: property,
      direction: direction === 'asc' ? 'desc' : 'asc',
    });
  };
  const className = `tableHeaderSortButton sortDirection_${direction}`;
  return (
    <th aria-sort={isSorted ? 'sort' : 'none'}>
      <span
        className={className}
        role="button"
        title={`sort by ${property}`}
        onClick={onClick}
      >
        {property}
      </span>
    </th>
  );
};

const TableRow = ({ beer }) => (
  <tr>
    <td>
      {getKey(keys.NAME)(beer)}
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
          <TableHeader property={keys.NAME} {...headerProps} />
          <TableHeader property={keys.BREWERY} {...headerProps} />
          <TableHeader property={keys.ABV} {...headerProps} />
          <TableHeader property={keys.COUNTRY} {...headerProps} />
        </tr>
      </thead>
      <tbody>
        {tableRows}
      </tbody>
    </table>);
};

export default BeerTable;
