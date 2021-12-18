import MessageCard from "../../UI/MessageCard";

const MessageDetail = (props) => {
    return (
        <MessageCard isUserMessage={props.isUserMessage}>
            <li>
                <div>
                    <h4>{props.text}</h4>
                    <div>
                        <b>{props.dateTime.substring(11, 19)}</b>
                        <text> {props.dateTime.substring(0, 10)} </text>
                    </div>
                    {!props.isUserMessage && <h7>From: {props.authorUsername}</h7>}
                </div>
            </li>
        </MessageCard>
    );
}

export default MessageDetail
