package com.safetynet.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.safetynet.api.exception.CRUDException;
import com.safetynet.api.model.SafetynetError;

@ControllerAdvice
public class ErrorController {
    @ExceptionHandler(value = { CRUDException.class })
    public ResponseEntity<SafetynetError> handlePersonErrors(CRUDException apiErrors) {
        var response = SafetynetError.builder()
                .error(apiErrors.getEnumException())
                .message(apiErrors.getMessage())
                .build();
        slf4jLogger.error("{}", response);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private final Logger slf4jLogger = LoggerFactory.getLogger(getClass().getName());
}
