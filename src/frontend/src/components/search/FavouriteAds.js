import React, {useEffect, useState} from "react";
import AdList from "../ad/AdList";
import {useHistory} from "react-router-dom";
import {Container} from "react-bootstrap";
import ReactPaginate from "react-paginate";


const FavouriteAds = () => {
    const history = useHistory();
    const [ads, setAds] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [pageSize, setPageSize] = useState(2);
    const [totalPages, setTotalPages] = useState(0);

    const fetchData = (pageNumber) => {
        fetch(`https://mytrade-bmucha.herokuapp.com/ad/favourite/adList?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
            credentials: "include",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                if (response.ok) {
                    return response.json()
                } else {
                    throw new Error("Sorry something went wrong")
                }
            })
            .then((data) => {
                setAds(data.content);
                setCurrentPage(data.number);
                setTotalPages(data.totalPages);
            });
        setIsDataFetched(true);
    };

    useEffect(() => {
        fetchData(currentPage);
    }, [])

    const handlePageClick = (data) => {
        const pageNumber = data.selected;
        fetchData(pageNumber);
        history.replace(`/profile/userFavouriteAds?pageNumber=${pageNumber}&pageSize=${pageSize}`)
    };

    return (
        <Container>
            {isDataFetched && (
                <div style={{marginTop: "5rem"}}>
                    <div className="text-center"><h1>Your favourite ads</h1></div>
                    <AdList data={ads}/>
                    <ReactPaginate
                        onPageChange={handlePageClick}
                        marginPagesDisplayed={2}
                        pageRangeDisplayed={2}
                        pageCount={totalPages}
                        renderOnZeroPageCount={null}
                        containerClassName={"pagination justify-content-center"}
                        pageClassName={"page-item"}
                        pageLinkClassName={"page-link"}
                        previousClassName={"page-item"}
                        previousLinkClassName={"page-link"}
                        nextClassName={"page-item"}
                        nextLinkClassName={"page-link"}
                        breakClassName={"page-item"}
                        breakLinkClassName={"page-link"}
                        activeClassName={"active"}
                    />
                </div>

            )}
        </Container>
    );
}
export default FavouriteAds;
