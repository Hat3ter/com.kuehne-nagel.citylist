package com.solbegsoft.sitylist.controllers;

import com.solbegsoft.sitylist.models.dtos.CityDto;
import com.solbegsoft.sitylist.models.reponse.ResponseApi;
import com.solbegsoft.sitylist.models.requests.CityRequest;
import com.solbegsoft.sitylist.services.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * City controller
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("api/v1/cities")
@RequiredArgsConstructor
@Validated
public class CityController {

    /**
     * @see CityService
     */
    private final CityService cityService;

    /**
     * Find by city name
     *
     * @param name city name
     * @return {@link ResponseApi}
     */
    @GetMapping("/{name}")
    public ResponseApi<CityDto> findByName(@PathVariable String name) {

        log.info("#GET: by name {}", name);
        CityDto result = cityService.findByName(name);
        log.info("#GET: by name {}, result {}", name, result);
        return new ResponseApi<>(result);
    }

    /**
     * Find all cities pageable
     *
     * @param page page
     * @param size size on the page
     * @return {@link ResponseApi}
     */
    @GetMapping
    public ResponseApi<List<CityDto>> findCities(@RequestParam Integer page,
                                                 @RequestParam Integer size) {

        log.info("#GET: all cities page/size {}/{}", page, size);
        List<CityDto> cities = cityService.findCities(page, size);
        log.info("#GET: all cities page/size {}/{}, result count {}", page, size, cities.size());
        return new ResponseApi<>(cities);
    }

    /**
     * Update city information (secured)
     *
     * @param cityRequest {@link CityRequest}
     * @return {@link ResponseApi}
     */
    @Secured({"ROLE_ALLOW_EDIT"})
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping
    public ResponseApi<CityDto> patchCity(@RequestBody @Valid CityRequest cityRequest) {

        log.info("#PATCH: id {}, city {}", cityRequest.getId(), cityRequest.getName());
        CityDto cityDto = cityService.updateCity(cityRequest);
        log.info("#PATCH: updated success id {}, city {}", cityRequest.getId(), cityRequest.getName());
        return new ResponseApi<>(cityDto);
    }
}
