import {useRef } from "react";
import AdList from "./AdList";
import { useState } from "react";
import Card from "./../UI/Card";

const TextSearch = (props) => {
  const [ads, setAds] = useState([]);

  const inputText = useRef()

  const searchHandler = (e) => {
    e.preventDefault();

    const encodedValue = encodeURIComponent(`%${inputText.current.value}%`);
    fetch(`http://localhost:8080/ad/search?searchText=${encodedValue}`)
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setAds(data.content);
      });
  };

  return (
    <Card>
      <input
        type="text"
        name="search"
        placeholder="Type your search here!"
        ref={inputText}
      />
      <button onClick={searchHandler}>Search!</button>
      <AdList ads={ads}></AdList>
    </Card>
  );
};

export default TextSearch;
