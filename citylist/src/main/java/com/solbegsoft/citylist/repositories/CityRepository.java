package com.solbegsoft.citylist.repositories;

import com.solbegsoft.citylist.models.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * City repository
 */
@Repository
public interface CityRepository extends JpaRepository<City, UUID> {

    /**
     * Find by name
     *
     * @param name ciry name
     * @return {@link City}
     */
    City findByName(String name);

    /**
     * Exists by name
     *
     * @param name city name
     * @return trye/false
     */
    boolean existsByName(String name);
}
