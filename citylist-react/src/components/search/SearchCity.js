import React, {useState} from "react";
import axios from "axios";
import City from "../cities/City";

const citiesUrl = `${process.env.REACT_APP_URL_CITY_API}/cities`;

export default function SearchCity({setToken, props}) {

    let [token, setNewToken] = useState(props.token.data);
    let [city, setCity] = useState()
    let [cityId, setId] = useState('')
    let [cityName, setCityName] = useState('')
    let [cityPhotoPath, setPhotoPath] = useState('')
    let [error, setError] = useState('')

    const getCity = async (e, name) => {

        e.preventDefault();

        if (!name || name.length === 0) {
            return;
        }
        await axios.get(`${citiesUrl}/${name}`, {
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Credentials': 'true',
                'Access-Control-Allow-Headers': 'Authorization',
                'Access-Control-Allow-Methods': 'POST, GET, PATCH, OPTIONS',
                'Authorization': JSON.parse(localStorage.getItem("token")).data
            }
        }).then((response) => {

            console.log("response", response)
            console.log("response data.data", (response.data.data))

            setId(response.data.data.id)
            setCityName(response.data.data.name)
            setPhotoPath(response.data.data.photoPath)

            setCity(
                {
                    id: response.data.data.id,
                    name: response.data.data.name,
                    photoPath: response.data.data.photoPath
                }
            )
            setError(null)
            setToken(response.headers["authorization"])
            setNewToken(response.headers["authorization"])
        }).catch(error => {
            console.error("Error:", error)
            setError("City not found")
        })
    }

    const renderCity = () => {

        if (error) {
            return (<h3>City not found</h3>)
        }

        if (city) {
            return (<City setToken={setToken} props={city}/>)
        }
    }

    return (
        <div className="login-wrapper">
            <form onSubmit={e => getCity(e, cityName)}>
                <label className="inline">
                    <p className="login-wrapper">City name </p>
                    <input type="text" onChange={e => setCityName(e.target.value)}/>
                </label>
                <div className="inline">
                    <button type="submit">get city</button>
                </div>
            </form>
            {renderCity()}
        </div>
    )

}