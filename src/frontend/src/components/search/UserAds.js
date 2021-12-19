import {useHistory} from "react-router-dom";
import React, {useEffect, useState} from "react";
import AdCard from "../UI/AdCard";
import ReactPaginate from "react-paginate";
import AdList from "../ad/AdList";

const UserAds = (props) => {
    const history = useHistory();
    const [ads, setAds] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [pageSize, setPageSize] = useState(2)

    const fetchData = (pageNumber) => {
        fetch(`http://localhost:8080/ad/adList/${props.username}?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                return response.json();
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
        history.replace(`/user/${props.username}?pageNumber=${pageNumber}&pageSize=${pageSize}`)
    };

    return (
        <AdCard>
            {isDataFetched && ( <AdList data={ads}/>)}
            {isDataFetched && (
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
            )}
        </AdCard>
    );
}

export default UserAds;