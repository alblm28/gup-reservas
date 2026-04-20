package io.github.alblm28.reserva_cabanas.dto.peticion;

import io.github.alblm28.reserva_cabanas.model.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CabaniaPeticion {

    @NotBlank
    @Size(max = 150)
    private String nombreCabania;

    private String descripcion;

    @NotNull
    @Min(1)
    private Short capacidadMax;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal precioNoche;

    @NotNull
    private EstadoCabania estado;

    @NotNull
    private TipoVentilacion ventilacion;

    @NotNull
    private TipoCama cama;

    @Min(0)
    private Short enchufes;

    @NotNull
    @Min(0)
    private Short numBanios;
}