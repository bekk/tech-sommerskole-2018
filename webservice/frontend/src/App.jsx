import React from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import Index from 'pages/Index';
import Details from 'pages/Details';
import ErrorPanel from 'components/ErrorPanel';

const App = () => (
  <Router>
    <React.Fragment>
      <nav className="main_menu">
        <Link to="/" className="main_menu_link link_home">
          {'Home'}
        </Link>
      </nav>
      <Route exact path="/" component={Index} />
      <Route path="/beer/:id(\d+)" component={Details} />
      <ErrorPanel />
    </React.Fragment>
  </Router>
);
export default App;
