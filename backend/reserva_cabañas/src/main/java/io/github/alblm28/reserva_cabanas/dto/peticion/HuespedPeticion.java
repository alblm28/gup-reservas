package io.github.alblm28.reserva_cabanas.dto.peticion;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;

@Data
public class HuespedPeticion {

    @NotBlank
    @Size(max = 20)
    private String nif;

    @NotBlank
    @Size(max = 30)
    private String nombre;

    @NotBlank
    @Size(max = 30)
    private String apellido1;

    @NotBlank
    @Size(max = 30)
    private String apellido2;

    @NotNull
    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate fechaNacimiento;


    @Size(max = 15)
    private String tel; // opcional

    @Email
    @Size(max = 300)
    private String email; // opcional
}