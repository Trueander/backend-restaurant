package com.abs.restaurant.app.exceptions;

public class BadRequestException extends RuntimeException{

    private static final String DESCRIPTION = "Bad request exception (400)";

    public BadRequestException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
