import { Fragment } from "react";
import { NavLink } from "react-router-dom";

import classes from "./Header.module.css";
// import logo from "../../assets/Logo.png";

const Header = () => {
  return (
    <Fragment>
      <header className={classes.header}>
        <h1 className={classes.logo}>[LOGO]TradeM</h1>
        <nav className={classes.nav}>
          <ul>
            <li>
              <NavLink activeClassName={classes.active} to="/welcome">
                Main
              </NavLink>
            </li>
            <li>
              <NavLink activeClassName={classes.active} to="/user">
                User
              </NavLink>
            </li>
            <li>
              <NavLink activeClassName={classes.active} to="/create-ad">
                Create Ad
              </NavLink>
            </li>
          </ul>
        </nav>
      </header>
    </Fragment>
  );
};

export default Header;
