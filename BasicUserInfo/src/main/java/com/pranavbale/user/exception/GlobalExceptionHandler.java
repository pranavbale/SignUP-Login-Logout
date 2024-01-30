package com.pranavbale.user.exception;

import com.pranavbale.user.entity.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    private ResponseEntity<APIResponse> CatchUserNotFoundException(UserException ex) {
        APIResponse apiResponse =
                APIResponse.builder()
                        .massage(ex.getMessage())
                        .success(false)
                        .code(HttpStatus.BAD_REQUEST)
                        .build();

        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}
