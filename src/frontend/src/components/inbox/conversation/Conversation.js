import React, {useContext, useEffect, useState} from "react";
import MessageList from "../message/MessageList";
import AuthContext from "../../../store/auth-context";
import validate from "../message/ValidateMessage";
import {Container} from "react-bootstrap";

const Conversation = ({match}) => {
    const authCtx = useContext(AuthContext);
    const [messages, setMessages] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const [enteredText, setEnteredText] = useState("");
    const [errors, setErrors] = useState({});
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => fetchMessages(), []);

    const fetchMessages = () => {
        fetch(`https://mytrade-bmucha.herokuapp.com/message/list/${match.params.id}`, {
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                if (response.ok) {
                    return response.json()
                } else {
                    throw new Error("Sorry something went wrong")
                }
            })
            .then((data) => {
                setMessages(data);
                setIsLoaded(true);
            })
    }

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
                if (response.ok) {
                    fetchMessages()
                } else {
                    throw new Error("Sorry something went wrong")
                }
            })
        }
    }, [errors]);

    return (
        <div>
            {isLoaded && (
                <Container>
                    <MessageList data={messages}/>
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
                </Container>)}
        </div>
    )


}

export default Conversation