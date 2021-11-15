import SearchResults from "../components/search/SearchResults";
import Card from '../components/UI/Card';

const SearchPage = ({match}) => {
    return (
        <Card>
            <SearchResults match={match}/>
        </Card>
      
    );
};

export default SearchPage;
