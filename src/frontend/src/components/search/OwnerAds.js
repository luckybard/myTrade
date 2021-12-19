import React, {useEffect, useState} from 'react'
import OwnerAdList from '../ad/OwnerAdList'
import ReactPaginate from "react-paginate";
import {useHistory} from "react-router-dom";
import {Container} from "react-bootstrap";

const OwnerAds = () => {
    const history = useHistory();
    const [ads, setAds] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    const fetchData = (pageNumber) => {
        fetch(`http://localhost:8080/ad/adList?pageNumber=${pageNumber}&pageSize=2`, {
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
        history.replace(`/profile/userAds?pageNumber=${pageNumber}&pageSize=2`)
    };

    return (
        <Container>
            {isDataFetched && (
                <div style={{marginTop: "5rem"}}>
                    <OwnerAdList data={ads}/>
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
                </div>)}
        </Container>
    );
}
export default OwnerAds
