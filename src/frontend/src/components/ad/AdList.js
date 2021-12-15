import {Link, Redirect, useHistory} from "react-router-dom";
import AdDetail from "./AdDetail";
import {Container} from "react-bootstrap";

const AdList = (props) => {
    return (
        <Container>
            <div style={{marginTop: "7rem"}}>
                <ul style={{listStyle: "none"}}>
                    {props.data.map((ad) => (
                        <Link style={{textDecoration: "none", color: 'black'}} to={`/ad/${ad.id}`}>
                            <AdDetail
                                key={ad.id}
                                id={ad.id}
                                category={ad.adCategory}
                                title={ad.title}
                                description={ad.description}
                                isHighlighted={ad.isHighlighted}
                            />
                        </Link>
                    ))}
                </ul>
            </div>
        </Container>
    );
};

export default AdList;
