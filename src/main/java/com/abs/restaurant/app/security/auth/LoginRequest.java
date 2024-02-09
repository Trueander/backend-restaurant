package com.abs.restaurant.app.security.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "El correo es obligatorio")
    private String email;

    @NotEmpty(message = "La constraseña es obligatorio")
    private String password;
}
