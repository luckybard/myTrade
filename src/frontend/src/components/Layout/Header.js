import {Fragment, useContext} from "react";
import {NavLink} from "react-router-dom";
import AuthContext from "../../store/auth-context";

import classes from "./Header.module.css";

const Header = () => {
    const authCtx = useContext(AuthContext);
    return (
        <Fragment>
            <header className={classes.header}>
                <h1 className={classes.logo}>MyTrade</h1>
                <nav className={classes.nav}>
                    <ul>
                        <li>
                            <NavLink activeClassName={classes.active} to="/home">
                                Home
                            </NavLink>
                        </li>
                        {authCtx.isLoggedIn &&
                        <li>
                            <NavLink activeClassName={classes.active} to="/profile">
                                Profile
                            </NavLink>
                        </li>
                        }
                        {!authCtx.isLoggedIn &&
                        <li>
                            <NavLink activeClassName={classes.active} to="/auth">
                                Login
                            </NavLink>
                        </li>
                        }
                        {authCtx.isLoggedIn && <li>
                            <NavLink activeClassName={classes.active} to="/create-ad">
                                Create Ad
                            </NavLink>
                        </li>}
                    </ul>
                </nav>
            </header>
        </Fragment>
    );
};

export default Header;
