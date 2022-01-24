import React from "react";
import City from "./City";

export default function CityList({setToken, cities}) {

    if (cities && cities.length > 0) {
        return (cities.map((city) => {
            return (<div><City key={city.id} setToken={setToken} props={city}/></div>)
        }))
    } else {
        return (<h3>Cities not found</h3>)
    }
}