import React from 'react';
import ReactDOM from 'react-dom';
import './index.less';

const title = 'Hello Bekk!';
ReactDOM.render(
    <div>{title}</div>,
    document.querySelector('#app'));

module.hot.accept();