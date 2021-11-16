import React, { useState, useEffect, useCallback } from "react";

const AuthContext = React.createContext({
  username: "",
  isLoggedIn: false,
  login: (username) => {},
  logout: () => {},
});

const retrieveStoredUsername = () => {
  const storedUsername = localStorage.getItem("username");
  return { username: storedUsername };
};

export const AuthContextProvider = (props) => {
  const usernameData = retrieveStoredUsername();
  let initialUsername;
  if(usernameData){
    initialUsername = usernameData.username;
  }
  const [username, setUsername] = useState(initialUsername);
  const userIsLoggedIn = !!username;

  const logoutHandler = useCallback(() => {
    setUsername(null);
    localStorage.removeItem("username", username);
  }, []);

  const loginHandler = (username) => {
    setUsername(username);
    localStorage.setItem("username", username);
  };

  const contextValue = {
    username: username,
    isLoggedIn: userIsLoggedIn,
    login: loginHandler,
    logout: logoutHandler
  };

  return (
    <AuthContext.Provider value={contextValue}>
      {props.children}
    </AuthContext.Provider>
  );
};

export default AuthContext;