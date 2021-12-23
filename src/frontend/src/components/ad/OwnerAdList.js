import {Link} from "react-router-dom";
import AdDetail from "./AdDetail";
import StatusButton from "./StatusButton";
import RefreshButton from "./RefreshButton";
import HighlightButton from "./HighlightButton";
import BackgroundCard from "../UI/BackgroundCard";
import React from "react";

const OwnerAdList = (props) => {
    return (
        <div>
            <div class="text-center"><h1>Your ads</h1></div>
            <ul style={{listStyle: "none"}}>
                {props.data.map((ad) => (
                    <div>
                        <Link style={{textDecoration: "none", color: 'black'}} to={`/edit/${ad.id}`}>
                            <AdDetail
                                key={ad.id}
                                title={ad.title}
                                description={ad.description}
                            />
                        </Link>
                        <BackgroundCard>
                            <div class="container">
                                <div class="row">
                                    <div class="col d-flex justify-content-center"><RefreshButton id={ad.id}
                                                                                                  isRefreshable={ad.isRefreshable}/>
                                    </div>
                                    <div className="col d-flex justify-content-center"><StatusButton id={ad.id}
                                                                                                     isActive={ad.isActive}/>
                                    </div>
                                    <div class="col d-flex justify-content-center"><HighlightButton id={ad.id}
                                                                                                    isUserAbleToHighlight={ad.isUserAbleToHighlight}/>
                                    </div>
                                </div>
                            </div>
                        </BackgroundCard>
                    </div>
                ))}
            </ul>
        </div>
    );
};
export default OwnerAdList;
