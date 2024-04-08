package com.abs.restaurant.app.security.auth;

import com.abs.restaurant.app.utils.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity<>(Utils.validateRequestErrors(result), HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(authService.login(request));
    }
}
