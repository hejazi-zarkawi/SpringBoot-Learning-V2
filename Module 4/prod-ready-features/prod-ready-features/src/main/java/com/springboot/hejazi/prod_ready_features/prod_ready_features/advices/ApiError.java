package com.springboot.hejazi.prod_ready_features.prod_ready_features.advices;

import lombok.*;
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
