import React from 'react'
import {Link} from "react-router-dom";
import ConversationDetail from "./ConversationDetail";

const ConversationList = (props) => {
    return (
        <ul style={{listStyle:"none"}}>
            {props.data.map((conversation) => (
                <Link style={{textDecoration:"none", color: 'black'}} to={`/conversation/${conversation.id}`}>
                    <ConversationDetail key={conversation.id}
                                        title={conversation.title}
                                        sender={conversation.senderUsername}
                                        recipient={conversation.recipientUsername}/>
                </Link>
            ))}
        </ul>
    );
};
export default ConversationList
