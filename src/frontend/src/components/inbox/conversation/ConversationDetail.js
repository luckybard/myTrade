import React from 'react'
import ConversationCard from "../../UI/ConversationCard";

const ConversationDetail = (props) => {
    return (
        <li>
            <ConversationCard>
                <h2>{props.title}</h2>
                <p>{props.recipient}</p>
                <p>{props.sender}</p>
            </ConversationCard>
        </li>
    );
}

export default ConversationDetail
