package com.example.cachingApp.advices;

import com.example.cachingApp.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.hibernate.StaleObjectStateException;
import org.hibernate.StaleStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getLocalizedMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<?> handleStaleObjectState(ObjectOptimisticLockingFailureException ex) {
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>("Stale data\n", HttpStatus.CONFLICT);
    }
}
