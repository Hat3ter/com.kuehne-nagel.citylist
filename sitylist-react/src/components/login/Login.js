import React, {useState} from 'react';
import PropTypes from 'prop-types';

async function loginUser(credentials) {

    const authLink = `${process.env.REACT_APP_URL_CITY_API}/authentication`;
    console.log("Login link", authLink)
    return fetch(authLink, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => data.json())
}

export default function Login({setToken}) {

    const [username, setUserName] = useState();
    const [password, setPassword] = useState();

    const handleSubmit = async e => {
        e.preventDefault();
        const tokenRaw = await loginUser({
            "login": username,
            "password": password
        });
        const token = JSON.stringify(tokenRaw);
        if (token.includes("Bearer")) {

            setToken(tokenRaw);
            localStorage.setItem("token", token)
        }
    }

    return (
        <div className="login-wrapper">
            <h1>Please Log In</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    <p>Username</p>
                    <input type="text" onChange={e => setUserName(e.target.value)}/>
                </label>
                <label>
                    <p>Password</p>
                    <input type="password" onChange={e => setPassword(e.target.value)}/>
                </label>
                <div>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>
    )
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};