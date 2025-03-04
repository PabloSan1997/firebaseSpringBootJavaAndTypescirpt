package com.ejemplo.springboor.firebase.springbootfirebase.exceptions;

public class MyBadRequestException extends RuntimeException{
    public MyBadRequestException() {
    }

    public MyBadRequestException(String message) {
        super(message);
    }
}
