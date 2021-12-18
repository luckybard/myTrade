import React, {useContext, useEffect, useState} from 'react'
import AuthContext from "../../store/auth-context";
import {useHistory} from "react-router-dom";
import ReactPaginate from "react-paginate";
import ConversationList from "./conversation/ConversationList";
import {Container} from "react-bootstrap";

const Inbox = () => {
    const authCtx = useContext(AuthContext);
    const history = useHistory();
    const [conversations, setConversations] = useState([]);
    const [isDataFetched, setIsDataFetched] = useState(false);
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [pageSize, setPageSize] = useState(2)

    const fetchData = (pageNumber) => {
        fetch(`http://localhost:8080/conversation/${authCtx.username}?pageNumber=${pageNumber}&pageSize=${pageSize}`, {
            credentials: "include",
            headers: {
                "Content-Type": "application/json",
            },
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                setConversations(data.content);
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
        history.replace(`/inbox?pageNumber=${pageNumber}&pageSize=${pageSize}`)
    };

    return (
        <Container>
            <div className="text-center" style={{marginTop: "5rem"}}><h1>Inbox</h1></div>
            {isDataFetched && (
                <div>
                    <ConversationList data={conversations}/>
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
export default Inbox
