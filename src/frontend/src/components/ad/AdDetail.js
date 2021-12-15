import React from 'react'
import AdCard from "../UI/AdCard";

const AdDetail = (props) => {
    return (
        <AdCard isHighlighted={props.isHighlighted}>
            <li>
                <h2>{props.title}</h2>
                <p>{props.description.substring(0, 100)}...</p>
            </li>
        </AdCard>
    );
}

export default AdDetail
