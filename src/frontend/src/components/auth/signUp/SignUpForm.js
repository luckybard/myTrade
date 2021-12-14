import React from "react";
import useSignUpForm from "../../../hooks/useSignUpForm";
import validate from "./ValidateSignUpInfo";
import { Link } from "react-router-dom";
import { Container } from "react-bootstrap";

const SignUpForm = () => {
  const { handleChange, handleSubmit, values, errors } =
    useSignUpForm(validate);

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
          <h1>Get started now! Sign up!</h1>
          <h5>Create your account by filling out the information below.</h5>
          <div className="form-group">
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
            <label htmlFor="email">Email</label>
            <input
              class="form-control"
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
              class="form-control"
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
              class="form-control"
              type="password"
              name="password2"
              placeholder="Enter your password again"
              value={values.password2}
              onChange={handleChange}
            />
            {errors.password2 && <p>{errors.password2}</p>}
          </div>
          <div style={{ marginTop: "0.5rem" }}>
            <button className="btn btn-primary" type="submit">
              Sign up!
            </button>
            <span>
              Already have an account? Login
              <Link to="/sign-in">
                <a href="/sign-in">here</a>
              </Link>
            </span>
          </div>
        </form>
      </div>
    </Container>
  );
};

export default SignUpForm;
