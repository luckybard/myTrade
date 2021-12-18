import React, {useContext, useEffect, useRef, useState} from "react";
import AuthContext from "../../../store/auth-context";
import validate from "./ValidateMessage";
import {useHistory} from "react-router";

const StartConversation = (props) => {
    const history = useHistory();
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
                    senderUsername: authCtx.username,
                    recipientUsername: props.ownerUsername,
                    title: props.title,
                    messageDtoList: [
                        {
                            authorUsername: authCtx.username,
                            text: enteredText
                        }
                    ]
                }),
            };
            fetch(`http://localhost:8080/conversation`, requestOptions).then(history.push("/inbox"));
        }
    }, [errors]);
    return <div>
        <label htmlFor="textMessage">Message</label>
        <input
            type="text"
            name="textMessage"
            placeholder="Enter your message"
            onChange={enteredTextChangeHandler}
        />
        {errors.text && <p>{errors.text}</p>}
        <button onClick={handleSubmit}>Send</button>
    </div>;
}

export default StartConversation