package com.solbegsoft.sitylist.models.reponse;

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
