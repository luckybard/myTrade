import React from "react";

import SignUpForm from "./SignUpForm";
import SignUpFormSuccess from "./SignUpFormSuccess";
import SignInFormSuccess from "./SignInFormSuccess";
import { useState } from "react";
import SignInForm from "./SignInForm";

const AuthForm = () => {
  const [isSignIn, setSignIn] = useState(false);
  const [signUpFormIsSubmitted, setSignUpFormIsSubmitted] = useState(false);
  const [signInFormIsSubmitted, setSignInFormIsSubmitted] = useState(false);

  const switchAuthModeHandler = () => {
    setSignIn((prevState) => !prevState);
  };

  const submitSignUpForm = () => {
    setSignUpFormIsSubmitted(true);
  };

  const submitSignInForm = () => {
    setSignInFormIsSubmitted(true);
  };

  return (
    <div>
      {isSignIn ? (
        <div>
          {!signInFormIsSubmitted ? (
            <SignInForm submitSignInForm={submitSignInForm} />
          ) : (
            <SignInFormSuccess />
          )}
        </div>
      ) : (
        <div>
          {!signUpFormIsSubmitted ? (
            <SignUpForm submitSignUpForm={submitSignUpForm} />
          ) : (
            <SignUpFormSuccess />
          )}
        </div>
      )}
      <button onClick={switchAuthModeHandler}>SignIn/SignUp</button>
    </div>
  );
};

export default AuthForm;
