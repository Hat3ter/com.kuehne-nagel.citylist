package com.solbegsoft.citylist.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for errors
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorApi {

    /**
     * message
     */
    private String message;
}
