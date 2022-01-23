package com.solbegsoft.sitylist.services;

import com.solbegsoft.sitylist.AbstractTestCaseTest;
import com.solbegsoft.sitylist.exceptions.CityNotFoundException;
import com.solbegsoft.sitylist.models.dtos.CityDto;
import com.solbegsoft.sitylist.models.entities.City;
import com.solbegsoft.sitylist.models.requests.CityRequest;
import com.solbegsoft.sitylist.repositories.CityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for {@link CityService}
 */
class CityServiceImplTest extends AbstractTestCaseTest {

    /**
     * @see CityService
     */
    @Autowired
    private CityService cityService;

    /**
     * @see CityRepository
     */
    @Autowired
    private CityRepository cityRepository;


    /**
     * Test for {@link CityService#findCities(Integer, Integer)}
     */
    @Test
    public void testShouldReturnValidData() {

        cityRepository.save(create("c"));
        cityRepository.save(create("a"));
        cityRepository.save(create("d"));
        cityRepository.save(create("b"));

        List<CityDto> cities = cityService.findCities(0, 2);
        assertNotNull(cities);
        assertEquals(2, cities.size());
        assertEquals("a", cities.get(0).getName());
        assertEquals("b", cities.get(1).getName());

        cities = cityService.findCities(1, 2);
        assertNotNull(cities);
        assertEquals(2, cities.size());
        assertEquals("c", cities.get(0).getName());
        assertEquals("d", cities.get(1).getName());
    }

    /**
     * Test for {@link CityService#findByName(String)}
     */
    @Test
    public void testFindByNameShouldThrowException() {

        assertThrows(CityNotFoundException.class, () -> {
            cityService.findByName("some name");
        }, "");

    }

    /**
     * Test for {@link CityService#findByName(String)}
     */
    @Test
    public void testFindByName() {

        cityRepository.save(create("b"));
        CityDto result = cityService.findByName("b");
        assertNotNull(result);
        assertEquals("b", result.getName());
    }

    /**
     * Test for {@link CityService#updateCity(CityRequest)}
     */
    @Test
    public void testUpdateCity() {

        CityRequest request = new CityRequest();
        request.setId(UUID.randomUUID());
        request.setName("name");
        request.setPhotoPath("photo path");


        assertThrows(CityNotFoundException.class, () -> {
            cityService.updateCity(request);
        });

        City city = cityRepository.save(create("new city name"));
        request.setId(city.getId());

        CityDto updatedCity = cityService.updateCity(request);
        assertEquals(request.getId(), updatedCity.getId());
        assertEquals(request.getName(), updatedCity.getName());
        assertEquals(request.getPhotoPath(), updatedCity.getPhotoPath());
    }


    /**
     * Create city
     *
     * @param name city name
     * @return {@link City}
     */
    private City create(String name) {

        City city = new City();
        city.setId(UUID.randomUUID());
        city.setName(name);
        city.setPhotoPath("some photo path");
        return city;
    }
}