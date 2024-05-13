package com.abs.restaurant.app.security.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "El correo es obligatorio")
    private String email;

    @NotEmpty(message = "La constraseña es obligatorio")
    private String password;
}
