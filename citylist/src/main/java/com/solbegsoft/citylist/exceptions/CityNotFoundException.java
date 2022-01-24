package com.solbegsoft.citylist.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception when city not found
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CityNotFoundException extends RuntimeException {

    /**
     * Constructor
     *
     * @param message message
     */
    public CityNotFoundException(String message) {
        super(message);
    }
}
