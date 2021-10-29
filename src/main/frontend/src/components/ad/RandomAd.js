import { useEffect } from "react";
import AdList from "./AdList";
import { useState } from "react";

const RandomAd = () => {
  const [ads, setAds] = useState([]);
  useEffect(
    () =>
      fetch(`http://localhost:8080/ad/fetch-random`, {credentials: "include", headers: {'Access-Control-Allow-Origin': '*', 'Content-Type': 'application/json'
    }})
        .then((response) => {
          return response.json();
        })
        .then((data) => {
          setAds(data.content);
        }),
    []
  );
  return <AdList ads={ads}></AdList>;
};

export default RandomAd;
