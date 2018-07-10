import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import './index.less';

const title = 'Hallo Bekk!';
ReactDOM.render(App(), document.querySelector('#app'));
module.hot.accept();