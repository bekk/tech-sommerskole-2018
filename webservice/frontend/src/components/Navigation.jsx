import React from 'react';
import { Link, withRouter } from 'react-router-dom';
import 'styles/navigation.less';

const NavLink = ({
                   path, current, className, children,
                 }) => {
  const fullClassName = `main_menu_link ${className}`;
  if (path === current) {
    return (
      <span className={fullClassName}>
        {children}
      </span>
    );
  }
  return (
    <Link to={path} className={fullClassName}>
      {children}
    </Link>
  );
};

const Navigation = props => (
  <nav className="main_menu">
    <NavLink
      path="/"
      current={props.location.pathname}
      className="link_home"
    >
      Home
    </NavLink>
  </nav>
);

export default withRouter(Navigation);
