import {Link} from "react-router-dom";
import Card from "../components/UI/Card";


const ProfilePage = () => {
    return (<Card>
        <Link to="/profile/userAds">User Ads </Link>
        <Link to="/profile/userFavouriteAds">User Favourite Ads</Link>)
    </Card>)

}
export default ProfilePage;
