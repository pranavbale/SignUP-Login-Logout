package com.pranavbale.user.exception;

public class InvalidInputParameterException extends RuntimeException{
    public InvalidInputParameterException() {
        super();
    }

    public InvalidInputParameterException(String massage) {
        super(massage);
    }
}
