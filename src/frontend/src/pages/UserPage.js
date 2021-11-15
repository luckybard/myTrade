import AdPagination from "../components/ad/AdPagination";

const UserPage = ({match}) => {
    return <div>
        <h1>{match.params.id}</h1>
        <AdPagination endPoint={`user/${match.params.id}`}/>
    </div>
};

export default UserPage;


