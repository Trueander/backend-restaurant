package com.abs.restaurant.app.exceptions;

public class ConflictException extends RuntimeException{

    public ConflictException(String message) {
        super(message);
    }
}
