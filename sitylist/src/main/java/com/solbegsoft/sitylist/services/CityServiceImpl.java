package com.solbegsoft.sitylist.services;

import com.solbegsoft.sitylist.exceptions.CityNotFoundException;
import com.solbegsoft.sitylist.models.dtos.CityDto;
import com.solbegsoft.sitylist.models.entities.City;
import com.solbegsoft.sitylist.models.requests.CityRequest;
import com.solbegsoft.sitylist.repositories.CityRepository;
import com.solbegsoft.sitylist.utils.CityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation {@link CityService}
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    /**
     * @see CityRepository
     */
    private final CityRepository cityRepository;

    @Override
    public CityDto findByName(String name) {

        return Optional
                .ofNullable(cityRepository.findByName(name))
                .map(CityConverter.INSTANCE::convert)
                .orElseThrow(() -> new CityNotFoundException(String.format("City with name %s not exists", name)));
    }

    @Override
    public List<CityDto> findCities(Integer page, Integer size) {

        return cityRepository.findAll(PageRequest.of(page, size, Sort.by("name"))).stream()
                .map(CityConverter.INSTANCE::convert)
                .collect(Collectors.toList());
    }

    @Override
    public CityDto updateCity(CityRequest cityRequest) {

        UUID cityId = cityRequest.getId();
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new CityNotFoundException(String.format("City with id %s not exists", cityId)));

        updateCity(cityRequest, city);
        return CityConverter.INSTANCE.convert(cityRepository.save(city));
    }

    /**
     * Update city
     *
     * @param cityRequest {@link CityRequest}
     * @param city        {@link City}
     */
    private static void updateCity(CityRequest cityRequest, City city) {
        city.setId(cityRequest.getId());
        city.setName(cityRequest.getName());
        city.setPhotoPath(cityRequest.getPhotoPath());
    }
}
