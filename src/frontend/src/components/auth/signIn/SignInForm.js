import React from "react";
import useSignInForm from "../../../hooks/useSignInForm";
import validate from "./ValidateSignInInfo";
import {Link} from "react-router-dom";

const SignInForm = () => {
    const {handleChange, handleSubmit, values, errors} = useSignInForm(
        validate
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
                    Don't have an account? Sign up! <Link to="/sign-up"><a href="/sign-up">here</a></Link>
                </span>
            </form>
        </div>
    );
};

export default SignInForm;
