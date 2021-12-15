import classes from './AdCard.module.css';

const AdCard = (props) => {
  return <div className={props.isHighlighted ? classes.highlighted : classes.normal}>{props.children}</div>;
}; 

export default AdCard;
