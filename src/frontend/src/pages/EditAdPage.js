import React, {useEffect, useState} from 'react'
import EditAd from "../components/editAd/EditAd";

const EditAdPage = ({match}) => {
    const [ad, setAd] = useState([])
    const [isFetched, setIsFetched] = useState(false);

    useEffect(
        () =>
            fetch(`http://localhost:8080/ad/edit/${match.params.id}`, {
                credentials: "include",
                headers: {
                    "Content-Type": "application/json",
                },
            })
                .then((response) => {
                    return response.json();
                })
                .then((data) => {
                    setAd(data);
                    setIsFetched(true);
                }),
        []
    );

    return (<div>
        {isFetched && <EditAd ad={ad}/>}
    </div>)
}

export default EditAdPage
