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
      `http://localhost:8080/user/favourite/${endpoint}/${props.id}`,
      requestOptions
    ).then(setIsFavourite((prevState) => !prevState));
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
