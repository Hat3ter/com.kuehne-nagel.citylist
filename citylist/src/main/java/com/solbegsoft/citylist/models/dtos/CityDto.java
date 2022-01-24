package com.solbegsoft.citylist.models.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * City dto
 */
@Data
@NoArgsConstructor
public class CityDto {

    /**
     * id
     */
    private UUID id;

    /**
     * city name
     */
    private String name;

    /**
     * photo path
     */
    private String photoPath;
}
