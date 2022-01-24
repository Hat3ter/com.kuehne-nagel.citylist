import React, {useEffect, useReducer, useState} from 'react'
import axios from "axios";
import CityList from "./CityList";

const citiesUrl = `${process.env.REACT_APP_URL_CITY_API}/cities`;

export default function Cities({setToken, props}) {

    const [items, setItems] = useState();
    const [page, setPage] = useState(0)

    const getAllCities = (page) => {

        axios.get(`${citiesUrl}?page=${page}&size=${5}`, {
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Credentials': 'true',
                'Access-Control-Allow-Headers': 'Authorization',
                'Access-Control-Allow-Methods': 'POST, GET, PATCH, OPTIONS',
                'Authorization': JSON.parse(localStorage.getItem("token")).data
            }
        }).then((response) => {
            console.log(response.data.data)
            setItems(response.data.data)
            setToken(response.headers["authorization"])
        }).catch(error => {
            console.error(error)
        })
    }

    useEffect(() => {
        console.log("use effect ", page)
        getAllCities(page)
    },[])



    function nextPage() {

        // e.preventDefault()
        console.log("Page before", page)
        setPage(page + 1)
        console.log("Page after", page)
        getAllCities(page + 1)
    }

    function previousPage() {

        // e.preventDefault()
        if (0 !== page) {
            console.log("Page before", page)
            setPage(page - 1)
            console.log("Page after", page)
            getAllCities(page - 1)
        }
    }

    return (
        <div className="login-wrapper">
            <h3>page number {page}</h3>
            <div>
                <CityList setToken={setToken} cities={items}/>
            </div>
            <div className="inline">
                <button onClick={previousPage}>previous page</button>
                <button onClick={nextPage}>next page</button>
            </div>
        </div>
    )
}
