import { Route } from "react-router";
import Header from "./components/Layout/Header";
import HomePage from "./components/pages/HomePage";
import PlaceHolder from "./components/pages/PlaceHolder";
import { Fragment } from "react";
import AuthPage from './components/pages/AuthPage';
import SearchPage from "./components/pages/SearchPage";
import Ad from "./components/ad/Ad";


function App() {
  return (
    <Fragment>
      <Header />
      <main>
        <Route path="/welcome">
          <HomePage></HomePage>
        </Route>
        <Route path="/user">
          <AuthPage></AuthPage>
        </Route>
        <Route path="/create-ad">
          <PlaceHolder></PlaceHolder>
        </Route>
        <Route path="/search">
          <SearchPage></SearchPage>
        </Route>
        <Route path="/search">
          <Ad></Ad>
        </Route>
      </main>
    </Fragment>
  );
}

export default App;
