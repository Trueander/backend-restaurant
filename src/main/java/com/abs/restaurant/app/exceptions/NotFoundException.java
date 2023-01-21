package com.abs.restaurant.app.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@NoArgsConstructor
public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
