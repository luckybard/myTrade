import UserAds from "../components/search/UserAds";

const UserPage = ({match}) => {
    return <div>
        <h1>{match.params.id}</h1>
        <UserAds username={match.params.id}/>
    </div>
};

export default UserPage;


