import React, {useState} from 'react'

const StatusButton = (props) => {
    const [isActive, setIsActive] = useState(props.isActive);

    const onClickHandler = () => {
        const requestOptions = {
            method: "PATCH",
        };
        fetch(`http://localhost:8080/ad/active/${props.id}`, requestOptions).then((response) => {
            if (response.ok) {
                setIsActive(prevState => !prevState);
            } else {
                throw new Error("Sorry something went wrong")
            }
        })

    }
    return (
        <div>
            <div>
                <div>{isActive ?
                    (
                        <div>
                            <p>Your ad is active, click button to change status</p>
                            <div style={{ display: "flex", justifyContent: "center" }}>
                                <button class="btn btn-success" onClick={onClickHandler}>Change status</button>
                            </div>
                        </div>
                    )
                    :
                    (
                        <div>
                            <p>Your ad is not-active, click button to change status</p>
                            <div style={{ display: "flex", justifyContent: "center" }}>
                                <button class="btn btn-secondary " onClick={onClickHandler}>Change status</button>
                            </div>
                            )}
                        </div>)}
                </div>
            </div>
        </div>
    );
}

export default StatusButton;
