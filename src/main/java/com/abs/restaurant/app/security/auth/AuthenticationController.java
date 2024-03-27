package com.abs.restaurant.app.security.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(validateRequestErrors(result), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(validateRequestErrors(result), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authService.login(request));
    }

    private Map<String, Object> validateRequestErrors(BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                .collect(Collectors.toList());

        response.put("errors", errors);
        return response;
    }
}
