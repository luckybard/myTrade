import React from "react";
import useSignUpForm from "../../../hooks/useSignUpForm";
import validate from "./ValidateSignUpInfo";
import {Link} from "react-router-dom";

const SignUpForm = () => {
  const { handleChange, handleSubmit, values, errors } = useSignUpForm(
    validate
  );
  
  return (
    <div>
      <form onSubmit={handleSubmit} noValidate>
        <h1>
          Get started now! Create your account by filling out the
          information below.
        </h1>
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
          <label htmlFor="email">Email</label>
          <input
            type="email"
            name="email"
            placeholder="Enter your email"
            value={values.email}
            onChange={handleChange}
          />
          {errors.email && <p>{errors.email}</p>}
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
        <div>
          <label htmlFor="password2">Confirm password</label>
          <input
            type="password"
            name="password2"
            placeholder="Enter your password again"
            value={values.password2}
            onChange={handleChange}
          />
          {errors.password2 && <p>{errors.password2}</p>}
        </div>
        <button type="submit">Sign up!</button>
        <span>
          Already have an account? Login
          <Link to="/sign-in"><a href="/sign-in">here</a></Link>
        </span>
      </form>
    </div>
  );
};

export default SignUpForm;
