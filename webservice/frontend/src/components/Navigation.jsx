import React from 'react';
import { Link, withRouter } from 'react-router-dom';
import 'styles/navigation.less';

const NavLink = ({ path, current, className, children }) => {
  const pathIsCurrent = path === current;
  const linkClass = pathIsCurrent ? 'main_menu_placeholder' : 'main_menu_link';
  const fullClassName = `${linkClass} ${className}`;
  if (pathIsCurrent) {
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
  <nav>
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
