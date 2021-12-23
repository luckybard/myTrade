import React, {useContext, useEffect, useState} from "react";
import {Redirect} from "react-router";
import AuthContext from "../../../store/auth-context";

const Logout = () => {
    const authCtx = useContext(AuthContext)
    const [isLogout, setIsLogout] = useState(false);

    useEffect(() => {
        fetch("https://mytrade-bmucha.herokuapp.com/logout").then(
            setIsLogout(true));
        authCtx.logout();
    }, []);

    return <div>{isLogout && <Redirect to="/sign-in"/>};</div>;
};

export default Logout;
