package com.abs.restaurant.app.utils;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static Map<String, Object> validateRequestErrors(BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        response.put("errors", errors);
        return response;
    }
}