package com.hejazi.securityApp.securityApp.advices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiError {

    private LocalDateTime timestamp;
    private String error;
    private HttpStatus status;

    public ApiError(){
        this.timestamp= LocalDateTime.now();
    }

    @Builder
    public ApiError(String error, HttpStatus status){
        this.timestamp= LocalDateTime.now();
        this.error= error;
        this.status= status;
    }
}
