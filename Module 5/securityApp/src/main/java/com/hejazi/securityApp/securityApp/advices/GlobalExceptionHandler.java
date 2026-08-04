package com.hejazi.securityApp.securityApp.advices;

import com.hejazi.securityApp.securityApp.exceptions.ResourceNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

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

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(
            BadCredentialsException exception) {

        ApiError apiError = ApiError.builder()
                .error("Invalid email or password")
                .status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJWT(
            ExpiredJwtException exception) {

        ApiError apiError = ApiError.builder()
                .error("JWT has been expired")
                .status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleInvalidJWT(
            JwtException exception) {

        ApiError apiError = ApiError.builder()
                .error("JWT is invalid")
                .status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(apiError,HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(
            AuthorizationDeniedException exception) {

        ApiError apiError = ApiError.builder()
                .error("Access is denied")
                .status(HttpStatus.FORBIDDEN).build();

        return new ResponseEntity<>(apiError,HttpStatus.FORBIDDEN);
    }
}
