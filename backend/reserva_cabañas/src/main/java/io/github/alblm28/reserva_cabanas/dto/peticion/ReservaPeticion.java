package io.github.alblm28.reserva_cabanas.dto.peticion;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ReservaPeticion {

    @NotNull
    private Integer idCabania;

    @NotNull
    private OffsetDateTime fechaInicio;

    @NotNull
    private OffsetDateTime fechaFin;

    @NotNull
    @Min(1)
    private Short numHuespedes;

    @NotNull
    @Size(min = 1, message = "Debe incluir al menos un huésped")
    private List<HuespedPeticion> huespedes;
}