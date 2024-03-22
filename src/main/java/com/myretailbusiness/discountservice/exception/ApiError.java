package com.myretailbusiness.discountservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter
@Setter
public class ApiError {

    private LocalDateTime timestamp;
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }
}

