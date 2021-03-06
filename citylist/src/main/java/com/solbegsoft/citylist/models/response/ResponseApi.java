package com.solbegsoft.citylist.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response model
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
public class ResponseApi<T> {

    /**
     * Response data
     */
    private T data;
}
