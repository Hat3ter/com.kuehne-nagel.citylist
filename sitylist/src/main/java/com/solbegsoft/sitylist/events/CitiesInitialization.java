package com.solbegsoft.sitylist.events;

import com.solbegsoft.sitylist.models.entities.City;
import com.solbegsoft.sitylist.repositories.CityRepository;
import com.solbegsoft.sitylist.utils.CsvReader;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Initialize cities from csv
 */
@Slf4j
@Service
@Profile("!test")
@RequiredArgsConstructor
public class CitiesInitialization {

    /**
     * @see CityRepository
     */
    private final CityRepository cityRepository;


    /**
     * Event for initialization
     */
    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        log.info("Start initialization cities from csv file");
        Resource resource = new ClassPathResource("cities.csv");
        InputStream inputStream = resource.getInputStream();
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        List<City> cities = CsvReader.convertToCityList(CsvReader.read(reader));
        log.info("Cities count {}", cities.size());
        cities.forEach(this::saveCity);
        log.info("Initialization cities from csv file success");
    }

    /**
     * Save city
     *
     * @param city {@link City}
     */
    private void saveCity(City city) {

        if (cityRepository.existsByName(city.getName())) {
            log.info("Duplicated city {}", city.getName());
        } else {
            cityRepository.save(city);
        }
    }
}
