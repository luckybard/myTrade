import React, {useContext} from 'react'
import MessageDetail from "./MessageDetail";
import {Container} from "react-bootstrap";
import AuthContext from "../../../store/auth-context";

const MessageList = (props) => {
    const authCtx = useContext(AuthContext);
    return (
        <Container>
            <div style={{marginTop: "7rem"}}>
                <ul style={{listStyle: "none"}}>
                    {props.data.map((message) => (
                        <MessageDetail key={message.id}
                                       isUserMessage={authCtx.username === message.authorUsername}
                                       authorUsername={message.authorUsername}
                                       text={message.text}
                                       dateTime={message.dateTime}/>
                    ))}
                </ul>
            </div>
        </Container>
    );
};
export default MessageList;
