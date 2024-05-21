package com.abs.restaurant.app.security.auth;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "El nombre es obligatorio")
    @Size(min = 2, max = 60, message = "El nombre debe tener entre 2 a 60 caracteres")
    private String firstname;

    @Size(min = 2, max = 60, message = "El apellido debe tener entre 2 a 60 caracteres")
    private String lastname;

    @NotEmpty(message = "El dni es obligatorio")
    @Size(min = 8, max = 8, message = "El dni debe tener 8 caracteres")
    @Pattern(regexp = "\\d{8}", message = "El dni debe contener solo números")
    private String dni;

    @Size(min = 9, max = 9, message = "El celular debe tener 9 caracteres")
    private String phoneNumber;

    @Email(message = "Correo inválido")
    @NotEmpty(message = "El correo es obligatorio")
    private String email;

    @NotNull(message = "El rol es obligatorio")
    private Set<Integer> roleIds;
}
