import React, {useState} from 'react';

import './App.css';
import Login from './components/login/Login';
import {Link} from "react-router-dom";
import {Route, Routes} from "react-router";
import Cities from "./components/cities/Cities";
import SearchCity from "./components/search/SearchCity";


function App() {

    const [token, setToken] = useState();

    if (!token) {
        return <Login setToken={setToken}/>
    }

    console.log("app token ", token)
    let props = {token: token, page: 0};
    return (
        <div className="login-wrapper">
            <div className="login-wrapper">
                <div>
                    <ul>
                        <li className="inline"><Link to={{pathname: "/"}}>Home</Link></li>
                        <li className="inline"><Link to={{pathname: "/search"}}>Search</Link></li>
                        <li className="inline"><a href="/" onClick={() => setToken(null)}>Quit</a></li>
                    </ul>
                </div>
            </div>

            <div>
                <Routes>
                    <Route path="/" element={<Cities setToken={setToken} props={props}/>}/>
                    <Route path="/search" element={<SearchCity setToken={setToken} props={props}/>}/>
                </Routes>
            </div>
        </div>
    );
}

export default App;