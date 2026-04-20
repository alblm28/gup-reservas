package io.github.alblm28.reserva_cabanas.dto.peticion;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioPeticion {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 30)
    private String nombre;

    @NotBlank(message = "El primer apellido es obligatorio")
    @Size(max = 30)
    private String apellido1;

    @NotBlank(message = "El segundo apellido es obligatorio")
    @Size(max = 30)
    private String apellido2;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene formato válido")
    @Size(max = 300)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasenia;

    @Size(max = 15)
    private String tel; // opcional
}
