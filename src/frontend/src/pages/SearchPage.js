import MainSearchResults from "../components/search/MainSearchResults";
import Card from '../components/UI/Card';

const SearchPage = ({match}) => {
    return (
            <MainSearchResults match={match}/>
    )
};

export default SearchPage;
