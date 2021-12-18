 import { Route, Switch, Redirect } from "react-router";
import Header from "./components/Layout/Header";
import HomePage from "./pages/HomePage";
import {Fragment, useContext} from "react";
import AuthPage from "./pages/AuthPage";
import SearchPage from "./pages/SearchPage";
import Ad from "./components/ad/Ad";
import CreateAdPage from "./pages/CreateAdPage";
import NotFound from "./pages/NotFound";
import AuthContext from "./store/auth-context";
import SignInForm from "./components/auth/signIn/SignInForm";
import SignUpForm from "./components/auth/signUp/SignUpForm";
import ProfilePage from "./pages/ProfilePage";
import UserAdsPage from "./components/search/OwnerAds";
import FavouriteAdsPage from "./pages/FavouriteAdsPage";
import EditAdPage from "./pages/EditAdPage";
import LogoutPage from "./pages/LogoutPage";
import UserPage from "./pages/UserPage";
import ConversationPage from "./pages/ConversationPage";
import InboxPage from "./pages/InboxPage";

function App() {
  const authCtx = useContext(AuthContext);
  return (
    <Fragment>
      <Header />
      <main>
        <Switch>
          <Route exact path="/">
            <Redirect to="/home" />
          </Route>
          {authCtx.isLoggedIn && <Route exact path="/create-ad" component={CreateAdPage} />}
          {!authCtx.isLoggedIn &&  <Route exact path="/create-ad"><Redirect to='auth'/></Route>  }
          <Route exact path="/home" component={HomePage} />
          <Route path="/edit/:id" component={EditAdPage} />
          <Route exact path="/profile" component={ProfilePage} />
          <Route exact path="/profile/userAds" component={UserAdsPage} />
          <Route path="/profile/inbox" component={InboxPage}/>
          <Route path="/profile/userFavouriteAds" component={FavouriteAdsPage} />
          <Route exact path="/sign-in" component={SignInForm} />
          <Route exact path="/sign-up" component={SignUpForm} />
          <Route exact path="/auth" component={AuthPage} />
          <Route path="/search=:id" component={SearchPage}/>
          <Route path="/ad/:id" component={Ad} />
          <Route path="/user/:id" component ={UserPage}/>
          <Route exact path="/logout" component={LogoutPage}/>
          <Route path="/conversation/:id" component={ConversationPage} />
          <Route path='*' component={NotFound} />
        </Switch>
      </main>
    </Fragment>
  );
}

export default App;
