import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Index from 'pages/Index';
import Details from 'pages/Details';
import ErrorPanel from 'components/ErrorPanel';
import Navigation from 'components/Navigation';

const App = () => (
  <Router>
    <React.Fragment>
      <Navigation/>
      <Route exact path="/" component={Index} />
      <Route path="/beer/:id(\d+)" component={Details} />
      <ErrorPanel />
    </React.Fragment>
  </Router>
);
export default App;
