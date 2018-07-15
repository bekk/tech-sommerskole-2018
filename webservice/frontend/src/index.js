import ReactDOM from 'react-dom';
import App from './App';
import 'reset-css';
import './index.less';

ReactDOM.render(App(), document.querySelector('#app'));
module.hot.accept();
