import {useEffect, useState} from "react";
import AdList from "../ad/AdList";
import {Container} from "react-bootstrap";

const RandomAds = () => {
    const [ads, setAds] = useState([]);

    useEffect(
        () =>
            fetch(`http://localhost:8080/ad/random?pageSize=5`, {
                credentials: "include",
                headers: {
                    "Access-Control-Allow-Origin": "*",
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    return response.json();
                })
                .then((data) => {
                    setAds(data.content);
                }),
        []
    );
    return (
        <Container>
            <AdList data={ads}/>
        </Container>);
};

export default RandomAds;