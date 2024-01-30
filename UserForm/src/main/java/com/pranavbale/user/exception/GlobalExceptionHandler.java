package com.pranavbale.user.exception;

import com.pranavbale.user.entity.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidInputParameterException.class)
    private ResponseEntity<APIResponse> catchInvalidInputParameterException(InvalidInputParameterException ex) {
        APIResponse apiResponse =
                APIResponse.builder()
                        .message(ex.getMessage())
                        .success(false)
                        .status(HttpStatus.BAD_REQUEST)
                        .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
