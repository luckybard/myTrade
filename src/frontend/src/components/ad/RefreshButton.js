import React, {useState} from "react";
import {BsArrowClockwise} from "react-icons/bs";

const RefreshButton = (props) => {
    const [isRefreshable, setIsRefreshable] = useState(props.isRefreshable);

    const onClickHandler = () => {
        const requestOptions = {
            method: "PATCH",
        };
        fetch(
            `https://mytrade-bmucha.herokuapp.com/ad/refresh/${props.id}`,
            requestOptions
        ).then((response) => {
            if (response.ok) {
                setIsRefreshable(false);
            } else {
                throw new Error("Sorry something went wrong")
            }
        })
    };

    return (
        <div>
            {isRefreshable ? (
                <div>
                    <p>Click on <BsArrowClockwise size={16}/> to refresh your ad!</p>
                    <div style={{ display: "flex", justifyContent: "center" }}>
                    <BsArrowClockwise size={32} onClick={onClickHandler}/>
                    </div>
                </div>
            ) : (
                <p>
                    Currently you can't refresh your ad, wait one week after last refresh
                </p>
            )}
        </div>
    );
};

export default RefreshButton;
