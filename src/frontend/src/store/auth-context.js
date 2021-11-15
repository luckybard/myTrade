import React, { useState, useEffect, useCallback } from 'react';

const AuthContext = React.createContext({
  username: '',
  isLoggedIn: false,
  login: (username) => {},
  logout: () => {},
});

export const AuthContextProvider = (props) => {
  const [username, setUsername] = useState('');
  const userIsLoggedIn = !!username;

  const logoutHandler = () => {
    setUsername(null);
  }

  const loginHandler = (username) => {
    setUsername(username);
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