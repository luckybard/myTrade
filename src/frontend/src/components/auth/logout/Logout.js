import React, {useContext, useEffect, useState} from "react";
import {Redirect} from "react-router";
import AuthContext from "../../../store/auth-context";

const Logout = () => {
    const authCtx = useContext(AuthContext)
    const [isLogout, setIsLogout] = useState(false);

    useEffect(() => {
        fetch("http://localhost:8080/logout").then(
            setIsLogout(true));
        authCtx.logout();
    }, []);

    return <div>{isLogout && <Redirect to="/sign-in"/>};</div>;
};

export default Logout;
