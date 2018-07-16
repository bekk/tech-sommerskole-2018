import React from 'react';
import { keys, getKey } from 'utils/beerUtils';

const BeerDetails = ({ beer }) => {
  if (!beer) return null;
  return (
    <div className="beer_detail">
      <span className="beer_detail_label">
        {keys.NAME}
      </span>
      <span className="beer_detail_info">
        {getKey(keys.NAME)(beer)}
      </span>
    </div>
  );
};

export default BeerDetails;
