import {Link} from "react-router-dom";
import AdDetail from "./AdDetail";
import StatusButton from "./StatusButton";
import RefreshButton from "./RefreshButton";
import HighlightButton from "./HighlightButton";
import AdCard from "../UI/AdCard";

const OwnerAdList = (props) => {
    return (
        <ul style={{listStyle: "none"}}>
            {props.data.map((ad) => (
                <AdCard>
                    <Link style={{textDecoration: "none", color: 'black'}} to={`/edit/${ad.id}`}>
                        <div>
                            <AdDetail
                                key={ad.id}
                                title={ad.title}
                                description={ad.description}
                            />
                        </div>
                    </Link>
                    <div class="container">
                        <div class="row" >
                            <div class="col d-flex justify-content-center"><RefreshButton id={ad.id} isRefreshable={ad.isRefreshable}/></div>
                            <div className="col d-flex justify-content-center"><StatusButton id={ad.id}
                                                                                             isActive={ad.isActive}/>
                            </div>
                            <div class="col d-flex justify-content-center"><HighlightButton id={ad.id}
                                                                  isUserAbleToHighlight={ad.isUserAbleToHighlight}/>
                            </div>
                        </div>
                    </div>
                </AdCard>
            ))}
        </ul>
    );
};
export default OwnerAdList;
