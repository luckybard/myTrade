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
            `https://mytrade-bmucha.herokuapp.com/ad/highlight/${props.id}`,
            requestOptions
        ).then((response) => {
            if (response.ok) {
                setIsHighlighted(true);
                setIsUserAbleToHighlight(false);
            } else {
                throw new Error("Sorry something went wrong")
            }
        })
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
