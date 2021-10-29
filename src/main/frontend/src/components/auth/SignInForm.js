import React from "react";
import useSignInForm from "../../hooks/useSignInForm";
import validate from "./ValidateSignInInfo";
import AuthContext from "../../store/auth-context";
import { useContext } from "react";

const SignInForm = ({ submitSignInForm }) => {
  const context = useContext(AuthContext);
  const { handleChange, handleSubmit, values, errors } = useSignInForm(
    submitSignInForm,
    validate,
    context
  );

  return (
    <div>
      <form onSubmit={handleSubmit} noValidate>
        <h1>Sign in!</h1>
        <div>
          <label htmlFor="username">Username</label>
          <input
            type="text"
            name="username"
            placeholder="Enter your username"
            value={values.username}
            onChange={handleChange}
          />
          {errors.username && <p>{errors.username}</p>}
        </div>
        <div>
          <label htmlFor="password">Password</label>
          <input
            type="password"
            name="password"
            placeholder="Enter your password"
            value={values.password}
            onChange={handleChange}
          />
          {errors.password && <p>{errors.password}</p>}
        </div>
        <button type="submit">Sign in!</button>
        <span>
          Don't have an account? Sign up!
          <a href="#">here</a>
        </span>
      </form>
    </div>
  );
};

export default SignInForm;
