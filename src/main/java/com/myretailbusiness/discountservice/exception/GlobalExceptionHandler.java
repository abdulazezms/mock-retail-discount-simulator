package com.myretailbusiness.discountservice.exception;

import com.mongodb.ErrorCategory;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> noResourceFoundExceptionHandler(NoResourceFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<Object> handleMongoWriteException(MongoWriteException ex) {
        if (ex.getError().getCategory().equals(ErrorCategory.DUPLICATE_KEY)) {
            String errorMessage = "A document with the same key already exists.";
            return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(String.format("An error occurred: %s", ex.getError().getCategory()), HttpStatus.BAD_REQUEST);
    }
}
