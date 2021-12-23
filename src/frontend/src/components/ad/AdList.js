import {Link, Redirect, useHistory} from "react-router-dom";
import AdDetail from "./AdDetail";

const AdList = (props) => {
    return (
            <div>
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
    );
};

export default AdList;
