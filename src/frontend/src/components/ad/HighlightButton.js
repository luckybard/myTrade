import React, {useState} from "react";
import {BsFillLightningChargeFill} from "react-icons/bs";

const HighlightButton = (props) => {
    const [isHighlighted, setIsHighlighted] = useState(props.isHighlighted);
    const [isUserAbleToHighlight, setIsUserAbleToHighlight] = useState(
        props.isUserAbleToHighlight
    );

    const onClickHandler = () => {
        const requestOptions = {
            method: "PATCH",
        };
        fetch(
            `http://localhost:8080/ad/highlight/${props.id}`,
            requestOptions
        ).then(setIsHighlighted(true));
        setIsUserAbleToHighlight(false);
    };
    return (
        <div className={"d-flex justify-content-center"}>
            {isUserAbleToHighlight ? (
                <div>
                    <p>Click on <BsFillLightningChargeFill size={16}/> to highlight your ad</p>
                    <div style={{ display: "flex", justifyContent: "center" }}>
                    <BsFillLightningChargeFill  size={32} onClick={onClickHandler}/>
                    </div>
                </div>
            ) : (
                <div>
                    {isHighlighted ? (
                        <p>Ad is already highlighted</p>
                    ) : (
                        <p>Currently you cannot highlight your ad</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default HighlightButton;