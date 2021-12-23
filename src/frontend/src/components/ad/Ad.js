import React, {useEffect, useState, useContext} from "react";
import AdCard from "../UI/AdCard";
import FavouriteButton from "./FavouriteButton";
import {Link} from "react-router-dom";
import StartConversation from "../inbox/message/StartConversation";
import AuthContext from "../../store/auth-context";
import {Container} from "react-bootstrap";

const Ad = ({match}) => {
    const authCtx = useContext(AuthContext);
    const [ad, setAd] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);

    useEffect(
        () =>
            fetch(`http://localhost:8080/ad/${match.params.id}`, {
                headers: {
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
                    setAd(data);
                    setIsLoaded(true);
                }),
        []
    );

    return (
        <Container>
            <div style={{marginTop: "7rem"}}>
                <AdCard isHighlighted={false}>
                    {isLoaded && (
                        <div>
                            <h1>{ad.title}</h1>
                            <h4 style={{textAlign: 'right'}}>Category:{ad.adCategory}</h4>
                            <h5>{ad.description}</h5>
                            <h6 style={{textAlign: 'right'}}>Price: ${ad.price}</h6>
                            <h6 style={{textAlign: 'right'}}>City:{ad.city}</h6>
                            {(authCtx.isLoggedIn && authCtx.username !== ad.ownerUsername) && (
                                <FavouriteButton
                                    id={ad.id}
                                    isUserFavourite={ad.isUserFavourite}
                                />)}
                            <h6>Owner:{ad.ownerUsername}</h6>
                            <Link to={`/user/${ad.ownerUsername}`}>Other ads from this user</Link>
                            {(authCtx.isLoggedIn && authCtx.username !== ad.ownerUsername) && (
                                <div className="d-md-flex justify-content-md-end">
                                <StartConversation
                                    ownerUsername={ad.ownerUsername}
                                    title={ad.title}
                                />
                                </div>
                            )}
                        </div>
                    )}
                </AdCard>
            </div>
        </Container>
    );
};

export default Ad;
