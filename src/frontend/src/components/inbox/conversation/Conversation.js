import React, {useEffect, useState} from "react";
import MessageList from "../message/MessageList";
import SendMessage from "../message/SendMessage";

const Conversation = ({match}) => {
    const [messages, setMessages] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(
        () =>
            fetch(`http://localhost:8080/message/list/${match.params.id}`, {
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    return response.json();
                })
                .then((data) => {
                    setMessages(data);
                    setIsLoaded(true);
                }), []);
    return (
        <div>
            {isLoaded && (
                <div>
                    <MessageList data={messages}/>
                    <SendMessage match={match}/>
                </div>)}
        </div>
    )


}

export default Conversation