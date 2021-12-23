import React, {useEffect, useState} from 'react'
import EditAdForm from "../components/editAd/EditAdForm";

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
                    if (response.ok) {
                        return response.json()
                    } else {
                        throw new Error("Sorry something went wrong")
                    }
                })
                .then((data) => {
                    setAd(data);
                    setIsFetched(true);
                }),
        []
    );

    return (<div>
        {isFetched && <EditAdForm ad={ad}/>}
    </div>)
}

export default EditAdPage
