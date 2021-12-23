import React, {useEffect, useState} from "react";
import AdList from "../ad/AdList";
import {Container} from "react-bootstrap";

const RandomAds = () => {
    const [ads, setAds] = useState([]);

    useEffect(
        () =>
            fetch(`https://mytrade-bmucha.herokuapp.com/ad/random?pageSize=5`, {
                credentials: "include",
                headers: {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    if (response.ok) {
                        return response.json()
                    } else {
                        throw new Error("Sorry something went wrong")
                    }
                })
                .then((data) => {
                    setAds(data.content);
                }),
        []
    );

    return (
        <Container>
            <div className="text-center"><h4>Random Advertisements</h4></div>
            <AdList data={ads}/>
        </Container>);
};

export default RandomAds;
