import React, {useContext, useEffect, useState} from "react";
import AuthContext from "../../../store/auth-context";
import validate from "./ValidateMessage";
import {useHistory} from "react-router";
import {Container} from "react-bootstrap";
import {useLocation} from "react-router-dom";

const SendMessage = ({match}) => {
    const history = useHistory();
    const location = useLocation();
    const authCtx = useContext(AuthContext);
    const [enteredText, setEnteredText] = useState("");
    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);

    const enteredTextChangeHandler = (e) => {
        setEnteredText(e.target.value);
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        setErrors(validate(enteredText));
        setIsSubmitting(true);
    };

    useEffect(() => {
        if (Object.keys(errors).length === 0 && isSubmitting) {
            const requestOptions = {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    authorUsername: authCtx.username,
                    text: enteredText,
                }),
            };
            fetch(`https://mytrade-bmucha.herokuapp.com/message/${match.params.id}`, requestOptions).then((response) => {
                if (!response.ok) {
                    throw new Error("Sorry something went wrong")
                }
            })
        }
    }, [errors]);
    return (
        <Container>
            <div style={{display: "flex", justifyContent: "center"}}>
                <label htmlFor="textMessage"/>
                <div className="form-group w-50" style={{display: "flex", justifyContent: "center"}}>
            <textarea
                class="form-control"
                rows="8"
                name="textMessage"
                placeholder="Enter your message"
                onChange={enteredTextChangeHandler}
            />
                    <button class="btn btn-warning" onClick={handleSubmit}>Send</button>
                </div>
            </div>
        </Container>)

}

export default SendMessage