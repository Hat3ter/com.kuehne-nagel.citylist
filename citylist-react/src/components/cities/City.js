import React, {useState} from "react";
import axios from "axios";

const citiesUrl = `${process.env.REACT_APP_URL_CITY_API}/cities`;

export default function City({setToken, props}) {

    let [isEdit, setIsEdit] = useState(false)
    let [city, setCity] = useState(props)
    let [edit, setEdit] = useState(true)
    let [cityId, setId] = useState(props.id)
    let [cityName, setCityName] = useState(props.name)
    let [cityPhotoPath, setPhotoPath] = useState(props.photoPath)
    let [errorMsg, setErrorMsg] = useState('')

    const handleSubmit = e => {

        e.preventDefault();
        let item = localStorage.getItem("token");
        let parsedToken = JSON.parse(item);
        axios.patch(`${citiesUrl}`, {
            "id": cityId,
            "name": cityName,
            "photoPath": cityPhotoPath
        }, {
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Credentials': 'true',
                'Access-Control-Allow-Headers': 'Authorization',
                'Access-Control-Allow-Methods': 'POST, GET, PATCH, OPTIONS',
                'Authorization': parsedToken.data
            }
        }).then((response) => {
            setToken(response.headers["authorization"])
            setCity(JSON.parse(JSON.stringify(response.data.data)))
        }).catch(error => {
            if (error.response.status === 403) {
                setToken(error.response.headers["authorization"])
                setErrorMsg("You haven't permissions to edit")
                console.log(errorMsg)
            } else {
                setCity(null)
                setErrorMsg(null)
                console.error(error)
            }
        })
    }

    let renderForm = () => {

        if (!city) {
            return (<></>);
        }

        return (
            <div>
                <h1>{errorMsg}</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        <p>City name</p>
                        <input type="text" value={cityName} onChange={e => setCityName(e.target.value)}/>
                    </label>
                    <label>
                        <p>photo url</p>
                        <input type="url" value={cityPhotoPath} onChange={e => setPhotoPath(e.target.value)}/>
                    </label>
                    <button type="submit">Edit</button>
                </form>
            </div>
        )
    }

    const changeEdit = (e) => {
        e.preventDefault();
        setIsEdit(!isEdit)
    }

    const renderEdit = () => {
        if (edit) {
            return (<button onClick={(e) => changeEdit(e)}>edit</button>)
        }
    }

    return (
        <div className="login-wrapper">
            <h2>{city.name}</h2>
            <div>
                <div className="inline">
                    <div className="inline">
                        <img alt={`${city.name} not found`} src={city.photoPath}
                             onError={() => setEdit(false)}/>
                    </div>
                    <div className="inline">
                        {renderEdit()}
                    </div>
                </div>
                <div className="inline">
                    {isEdit && renderForm()}
                </div>
            </div>
        </div>
    )

}