import React, {useState} from "react";
import axios from "axios";

const citiesUrl = `${process.env.REACT_APP_URL_CITY_API}/cities`;

export default function City({setToken, props}) {

    let [isEdit, setIsEdit] = useState(false)
    let [isRenderEdit, setIsRenderEdit] = useState(true)

    let [cityId, setId] = useState(props.id)
    let [cityName, setCityName] = useState(props.name)
    let [cityPhotoPath, setPhotoPath] = useState(props.photoPath)

    let [cityIdInput, setIdInput] = useState(props.id)
    let [cityNameInput, setCityNameInput] = useState(props.name)
    let [cityPhotoPathInput, setPhotoPathInput] = useState(props.photoPath)

    let [errorMsg, setErrorMsg] = useState('')

    const handleSubmit = e => {

        e.preventDefault();
        let item = localStorage.getItem("token");
        let parsedToken = JSON.parse(item);
        axios.patch(`${citiesUrl}`, {
            id: cityIdInput,
            name: cityNameInput,
            photoPath: cityPhotoPathInput
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
            const updateCity = JSON.parse(JSON.stringify(response.data.data));
            setId(updateCity.id)
            setCityName(updateCity.name)
            setPhotoPath(updateCity.photoPath)
            props.id = updateCity.id;
            props.name = updateCity.name;
            props.photoPath = updateCity.photoPath;
            setIdInput(updateCity.id)
            setCityNameInput(updateCity.name)
            setPhotoPathInput(updateCity.photoPath)
            setIsEdit(false)
        }).catch(error => {
            if (error.response.status === 403) {
                setToken(error.response.headers["authorization"])
                setErrorMsg("You haven't permissions to edit")
                console.log(errorMsg)
            } else {
                setErrorMsg(null)
                console.error(error)
            }
        })
    }

    let renderForm = () => {

        if (!props) {
            return (<></>);
        }

        return (
            <div>
                <h1>{errorMsg}</h1>
                <form onSubmit={handleSubmit}>
                    <label>
                        <p>City name</p>
                        <input type="text" value={cityNameInput} onChange={e => {
                            setCityNameInput(e.target.value)
                        }}/>
                    </label>
                    <label>
                        <p>photo url</p>
                        <input type="url" value={cityPhotoPathInput} onChange={e => {
                            setPhotoPathInput(e.target.value)
                        }}/>
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
        if (isRenderEdit) {
            return (<button onClick={(e) => changeEdit(e)}>edit</button>)
        }
    }

    return (
        <div className="login-wrapper">
            <h2>{props.name}</h2>
            <div>
                <div className="inline">
                    <div className="inline">
                        <img alt={`${props.name} not found`} src={props.photoPath} onError={() => setIsRenderEdit(false)}/>
                    </div>
                    <div className="inline">
                        <button onClick={(e) => changeEdit(e)}>edit</button>
                    </div>
                </div>
                <div className="inline">
                    {isEdit && renderForm()}
                </div>
            </div>
        </div>
    )

}