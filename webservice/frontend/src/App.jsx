import React from 'react';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';
import Index from 'pages/Index';

const App = () => (
  <Router>
    <React.Fragment>
      <nav>
        <Link to="/">
          {'List'}
        </Link>
      </nav>
      <Route exact path="/">
        <Index />
      </Route>
    </React.Fragment>
  </Router>
);
export default App;
