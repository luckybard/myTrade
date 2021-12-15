import classes from './AdCard.module.css';

const ConversationCard = (props) => {
    return <div className={classes.normal}>{props.children}</div>;
};

export default ConversationCard;
