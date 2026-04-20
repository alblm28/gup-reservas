package io.github.alblm28.reserva_cabanas.dto.peticion;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LoginPeticion {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String contrasenia;
}