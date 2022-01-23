package com.solbegsoft.sitylist.models.requests;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Request for city
 */
@Data
public class CityRequest {

    /**
     * id
     */
    @NotNull
    private UUID id;

    /**
     * city name
     */
    @NotBlank
    private String name;

    /**
     * photo path should be url
     */
    @NotBlank
    @URL
    private String photoPath;
}
