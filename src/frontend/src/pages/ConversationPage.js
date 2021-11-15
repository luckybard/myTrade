import React from 'react'
import Conversation from "../components/inbox/conversation/Conversation";

const ConversationPage = ({match}) => {
    return (
        <div>
            <Conversation match={match}/>
        </div>
    )
}

export default ConversationPage
