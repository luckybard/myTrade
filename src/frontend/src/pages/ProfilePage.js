import {Link} from "react-router-dom";
import React from "react";

const ProfilePage = () => {
    return (<div>
        <div className="position-absolute top-50 start-50 translate-middle btn-group-vertical">
            <Link style={{color: 'black'}}
                  class="btn btn-lg btn-outline-warning" to="/profile/inbox">Inbox</Link>
            <Link style={{color: 'black'}}
                  class="btn btn-lg btn-outline-warning" to="/profile/userAds">Your Ads</Link>
            <Link style={{color: 'black'}}
                  class="btn btn-lg btn-outline-warning" to="/profile/userFavouriteAds">Your Favourite
                Ads</Link>
            <button type="button" className="btn btn-lg btn-secondary" disabled/>
            <Link style={{color: 'black'}}
                  class="btn btn-lg btn-outline-danger" to="/logout">Logout</Link>
        </div>
    </div>)

}
export default ProfilePage;
