import React from "react";
import useSignInForm from "../../../hooks/useSignInForm";
import validate from "./ValidateSignInInfo";
import { Link } from "react-router-dom";
import { Container } from "react-bootstrap";

const SignInForm = () => {
  const { handleChange, handleSubmit, values, errors } =
    useSignInForm(validate);

  return (
    <Container>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignContent: "center",
          marginTop: "7rem",
        }}
      >
        <form onSubmit={handleSubmit} noValidate>
          <h1>Sign in!</h1>
          <div class="form-group">
            <div>
              <label htmlFor="username">Username</label>
              <input
                class="form-control"
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
                class="form-control"
                type="password"
                name="password"
                placeholder="Enter your password"
                value={values.password}
                onChange={handleChange}
              />
              {errors.password && <p>{errors.password}</p>}
            </div>
            <div style={{ marginTop: "0.5rem" }}>
              <button className="btn btn-primary" type="submit">
                Sign in!
              </button>
              <span>
                Don't have an account? Sign up!{" "}
                <Link to="/sign-up">
                  <a href="/sign-up">here</a>
                </Link>
              </span>
            </div>
          </div>
        </form>
      </div>
    </Container>
  );
};

export default SignInForm;
