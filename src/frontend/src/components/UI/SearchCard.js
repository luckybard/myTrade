import classes from "./SearchCard.module.css";

const SearchCard = (props) => {
    return <div className={classes.normal}>{props.children}</div>;
};

export default SearchCard;