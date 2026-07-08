package com.hejazi.securityApp.securityApp.advices;

import com.hejazi.securityApp.securityApp.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException exception){
        ApiError error= ApiError.builder()
                .error(exception.getLocalizedMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
