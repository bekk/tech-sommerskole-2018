import React from 'react';

const TextFilter = ({ id, property, onFilterChange }) => (
  <div className="text_filter">
    <label htmlFor={id}>
      {property}
    </label>
    <input
      id={id}
      placeholder={`Filter by ${property}`}
      onChange={e => onFilterChange({ value: e.target.value, key: property })}
    />
  </div>
);


export default TextFilter;
