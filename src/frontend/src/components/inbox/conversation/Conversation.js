import React, {useEffect, useState} from "react";
import MessageList from "../message/MessageList";
import SendMessage from "../message/SendMessage";

const Conversation = ({match}) => {
    const [messages, setMessages] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(
        () =>
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
                }), [SendMessage]);
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