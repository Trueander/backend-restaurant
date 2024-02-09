package com.abs.restaurant.app.exceptions;

public class ConflictException extends RuntimeException{

    private static final String DESCRIPTION = "Conflict exception (409)";

    public ConflictException(String message) {
        super(DESCRIPTION + ". " + message);
    }
}
