import classes from "./BackgroundCard.module.css";

const BackgroundCard = (props) => {
    return <div className={classes.normal}>{props.children}</div>;
};

export default BackgroundCard;