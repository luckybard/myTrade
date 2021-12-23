import React, {useEffect, useState} from "react";
import ReactPaginate from "react-paginate";
import AdList from "../ad/AdList";
import {useHistory} from "react-router-dom";
import {Container} from "react-bootstrap";
import SearchCard from "../UI/SearchCard";
import BackgroundCard from "../UI/BackgroundCard";

const AdvancedSearch = ({match}) => {
    const history = useHistory();
    const [searchedText, setSearchedText] = useState(match.params.id);
    const [ads, setAds] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);
    const [pageNumber, setPageNumber] = useState(0);
    const [pageSize, setPageSize] = useState(2);
    const [totalPages, setTotalPages] = useState(0);
    const [adCategory, setAdCategory] = useState('ALL');
    const [isSearchedInDescription, setIsSearchedInDescription] = useState(false);
    const [city, setCity] = useState('EVERYWHERE');
    const [priceFrom, setPriceFrom] = useState(0);
    const [priceTo, setPriceTo] = useState(2147483647);

    const fetchData = (pageNumber) => {
        setIsDataFetched(false);
        if (searchedText.trim()) {
            const encodedText = encodeURIComponent(`%${searchedText}%`);
            fetch(
                `http://localhost:8080/ad?searchText=${encodedText}&isSearchedInDescription=${isSearchedInDescription}
            &adCategory=${adCategory}&city=${city}&priceFrom=${priceFrom}&priceTo=${priceTo}&pageNumber=${pageNumber}&pageSize=${pageSize}`
            )
                .then((response) => {
                    if (response.ok) {
                        return response.json()
                    } else {
                        throw new Error("Sorry something went wrong")
                    }
                })
                .then((data) => {
                    setAds(data.content);
                    setPageNumber(data.number);
                    setTotalPages(data.totalPages);
                });
            setIsDataFetched(true);
            history.replace(`/search=${searchedText}`)
        }
    };

    useEffect(() => {
        fetchData(0);
    }, [])

    const handlePageChange = (data) => {
        fetchData(data.selected);
    };

    const enteredTextChangeHandler = (event) => {
        setSearchedText(event.target.value);
    };

    const enteredCategoryChangeHandler = (event) => {
        setAdCategory(event.target.value);
    };

    const enteredCityChangeHandler = (event) => {
        setCity(event.target.value);
    };

    const enteredPriceFromChangeHandler = (event) => {
        setPriceFrom(event.target.value);
    };

    const enteredPriceToChangeHandler = (event) => {
        setPriceTo(event.target.value);
    };

    const enteredIsSearchedDescriptionChangeHandler = () => {
        setIsSearchedInDescription((prevState) => !prevState);
    };

    const handleSearch = (e) => {
        e.preventDefault();
        fetchData(0);

    }

    return (
        <div style={{marginTop: "5rem"}}>
            <Container>
                <SearchCard>
                    <div className="text-center"><h4>Search with extra filters</h4></div>
                    <form noValidate>
                        <div className="row">
                            <div className="col-md-6 ">
                                <label htmlFor="title">Text</label>
                                <input
                                    className="form-control"
                                    type="text"
                                    name="text"
                                    defaultValue={searchedText}
                                    onChange={enteredTextChangeHandler}
                                />
                            </div>
                            <div className="col-md-2">
                                <label htmlFor="city">City</label>
                                <select
                                    className="form-select"
                                    name="city"
                                    onChange={enteredCityChangeHandler}
                                >
                                    <option value="WARSAW">Warsaw</option>
                                    <option value="LONDON">London</option>
                                    <option value="PARIS">Paris</option>
                                    <option value="MOSCOW">Moscow</option>
                                    <option value="PORTO">Porto</option>
                                    <option value="BERLIN">Berlin</option>
                                    <option selected value="EVERYWHERE">Everywhere</option>
                                </select>
                            </div>
                            <div className="col-md-2">
                                <label htmlFor="adCategory">Category</label>
                                <select
                                    className="form-select"
                                    name="adCategory"
                                    onChange={enteredCategoryChangeHandler}
                                >
                                    <option value="CLOTHES">Clothes</option>
                                    <option value="APPLIANCES">Appliances</option>
                                    <option value="BOOKS">Books</option>
                                    <option value="FURNITURE">Furniture</option>
                                    <option value="OTHER">Other</option>
                                    <option selected value="ALL">All</option>
                                </select>
                            </div>
                            <div className="col-md-1">
                                <label htmlFor=" price">Min</label>
                                <input
                                    className=" form-control"
                                    type=" number"
                                    min="0"
                                    max="1000000"
                                    name="priceFrom"
                                    onChange={enteredPriceFromChangeHandler}
                                />
                            </div>
                            <div className="col-md-1">
                                <label htmlFor=" price">Max</label>
                                <input
                                    className=" form-control"
                                    type=" number"
                                    min="0"
                                    max="1000000"
                                    name="priceTo"
                                    onChange={enteredPriceToChangeHandler}
                                />
                            </div>
                        </div>
                        <div>
                            <label htmlFor="description">Description should be checked</label>
                            <input
                                className="form-check-input"
                                type="checkbox"
                                value="true"
                                name="isDescription"
                                onChange={enteredIsSearchedDescriptionChangeHandler}
                            />
                        </div>
                        <div class="d-md-flex justify-content-md-end">
                            <button className="btn-lg btn-warning" onClick={handleSearch}>
                                Search!
                            </button>
                        </div>
                    </form>
                </SearchCard>
                {isDataFetched && (
                    <BackgroundCard>
                        <div>
                            <AdList data={ads}/>
                            <ReactPaginate
                                onPageChange={handlePageChange}
                                marginPagesDisplayed={2}
                                pageRangeDisplayed={2}
                                pageCount={totalPages}
                                renderOnZeroPageCount={null}
                                containerClassName={"pagination justify-content-center"}
                                pageClassName={"page-item"}
                                pageLinkClassName={"page-link warning"}
                                previousClassName={"page-item"}
                                previousLinkClassName={"page-link"}
                                nextClassName={"page-item"}
                                nextLinkClassName={"page-link"}
                                breakClassName={"page-item"}
                                breakLinkClassName={"page-link"}
                                activeClassName={"active"}
                            />
                        </div>
                    </BackgroundCard>
                )}
            </Container>
        </div>
)
;
}

export default AdvancedSearch
