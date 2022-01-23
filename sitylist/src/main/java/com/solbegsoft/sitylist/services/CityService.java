package com.solbegsoft.sitylist.services;

import com.solbegsoft.sitylist.models.dtos.CityDto;
import com.solbegsoft.sitylist.models.requests.CityRequest;

import java.util.List;

/**
 * Service for city
 */
public interface CityService {

    /**
     * Find by name
     *
     * @param name city name
     * @return {@link CityDto}
     */
    CityDto findByName(String name);

    /**
     * Get cities with pageable
     *
     * @param page page
     * @param size size on page
     * @return list of cities
     */
    List<CityDto> findCities(Integer page, Integer size);

    /**
     * Update city
     *
     * @param cityRequest {@link CityRequest}
     * @return
     */
    CityDto updateCity(CityRequest cityRequest);
}
