import React from 'react'
import classes from "./MessageCard.module.css";

const MessageCard = (props) => {
    return <div className={props.isUserMessage ? classes.user : classes.sender }>{props.children}</div>;
};

export default MessageCard
