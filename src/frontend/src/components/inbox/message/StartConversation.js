import React, {useContext, useEffect, useState} from "react";
import AuthContext from "../../../store/auth-context";
import validate from "./ValidateMessage";

const StartConversation = (props) => {
    const authCtx = useContext(AuthContext);
    const [enteredText, setEnteredText] = useState("");
    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [isMessageSent, setIsMessageSent] = useState(false);

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
            fetch(`http://localhost:8080/conversation`, requestOptions)
                .then((response) => {
                    if (response.ok) {
                        setIsMessageSent(true);
                    } else {
                        throw new Error("Sorry something went wrong")
                    }
                })
        }
    }, [errors]);

    return <div>
        {isMessageSent ? (
                <div>
                    <h6 style={{textAlign: 'center'}}>Message has been send, check your inbox</h6>
                </div>
            )
            :
            (
                <div className="form-group">
                    <div className="d-md-flex justify-content-md-end">
                    <textarea
                        class="form-control"
                        type="text"
                        rows="3"
                        name="textMessage"
                        placeholder="Enter your message to the add owner"
                        onChange={enteredTextChangeHandler}
                    />
                        <button className="btn btn-warning" onClick={handleSubmit}>Send</button>
                        {errors.text && <p>{errors.text}</p>}
                    </div>
                </div>)}
    </div>
}

export default StartConversation