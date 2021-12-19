import React, { useState } from "react";
import { Link } from "react-router-dom";

const HomePageSearch = () => {
  const [enteredText, setEnteredText] = useState(" ");

  const enteredTextChangeHandler = (event) => {
    setEnteredText(event.target.value);
  };

  return (
    <div>
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          marginTop: "5rem",
        }}
      >
        <h1>Search Your Ad</h1>
      </div>
      <div
        class="input-group"
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          marginBottom: "5rem"
        }}
      >
        <input
          class="input-group-text"
          type="text"
          onChange={enteredTextChangeHandler}
          placeholder="Type your search here"
        />
        <Link to={`/search=${enteredText}`}>
          <button class="btn btn-warning">Search</button>
        </Link>
      </div>
    </div>
  );
};

export default HomePageSearch;
