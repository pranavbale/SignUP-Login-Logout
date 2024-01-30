package com.pranavbale.user.exception;

public class UserException extends RuntimeException{
    public UserException() {
        super();
    }

    public UserException(String massage) {
        super(massage);
    }
}
