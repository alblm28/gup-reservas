package io.github.alblm28.reserva_cabanas.dto.peticion;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class PagoPeticion {

    @NotNull
    private Integer idReserva;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal cantidad;

    // ID devuelto por Stripe al completar el pago
    private String idTransaccionExterna;
}
