package com.abs.restaurant.app.exceptions;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
