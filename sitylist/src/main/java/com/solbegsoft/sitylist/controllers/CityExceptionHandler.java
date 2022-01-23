package com.solbegsoft.sitylist.controllers;

import com.solbegsoft.sitylist.exceptions.CityNotFoundException;
import com.solbegsoft.sitylist.models.reponse.ErrorApi;
import com.solbegsoft.sitylist.models.reponse.ResponseApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler
 */
@Slf4j
@RestControllerAdvice
public class CityExceptionHandler {

    /**
     * Handle {@link CityNotFoundException}
     *
     * @param e exception
     * @return {@link ResponseApi}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseApi<ErrorApi> handleCityNotFoundException(CityNotFoundException e) {

        log.info("Fetched exception {{}}", e.getMessage());
        return new ResponseApi<>(new ErrorApi(e.getMessage()));
    }

    /**
     * Handle {@link MissingServletRequestParameterException}
     *
     * @param e exception
     * @return {@link ResponseApi}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseApi<ErrorApi> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {

        log.info("Fetched exception {{}}", e.getMessage());
        return new ResponseApi<>(new ErrorApi(e.getMessage()));
    }

    /**
     * Handle {@link MethodArgumentNotValidException}
     *
     * @param e exception
     * @return {@link ResponseApi}
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseApi<ErrorApi> handleConstraintViolationException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldErrors().get(0);
        Object rejectedValue = fieldError.getRejectedValue();
        String field = fieldError.getField();
        String reason = fieldError.getDefaultMessage();
        String errorMessage = String.format("Validation failed %s {%s}, for value {%s}",
                field, reason, rejectedValue);
        log.info("Fetched exception {{}}", errorMessage);
        return new ResponseApi<>(new ErrorApi(errorMessage));
    }

    /**
     * Handle {@link BadCredentialsException}
     *
     * @param e exception
     * @return {@link ResponseApi}
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseApi<ErrorApi> handleBadCredentialsException(BadCredentialsException e) {

        log.info("Fetched exception {{}}", e.getMessage());
        return new ResponseApi<>(new ErrorApi(e.getMessage()));
    }
}
