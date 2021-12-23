import React, { useState } from "react";
import { BsHeart, BsFillHeartFill } from "react-icons/bs";

const FavouriteButton = (props) => {
  const [isFavourite, setIsFavourite] = useState(props.isUserFavourite);

  const onClickHandler = () => {
    let endpoint;
    if (isFavourite) {
      endpoint = "remove";
    } else {
      endpoint = "add";
    }
    const requestOptions = {
      method: "PATCH",
    };
    fetch(
      `https://mytrade-bmucha.herokuapp.com/user/favourite/${endpoint}/${props.id}`,
      requestOptions
    ).then((response) => {
      if (response.ok) {
          setIsFavourite((prevState) => !prevState);
      } else {
        throw new Error("Sorry something went wrong")
      }
    })
  };

  return (
    <div>
      {isFavourite ? (
        <BsFillHeartFill size={32} onClick={onClickHandler}/>
      ) : (
        <BsHeart size={32} onClick={onClickHandler} />
      )}
    </div>
  );
};

export default FavouriteButton;
