package io.github.alblm28.reserva_cabanas.dto.respuesta;

import io.github.alblm28.reserva_cabanas.model.EstadoPago;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class PagoRespuesta {

    private Integer idPago;
    private String idTransaccionExterna;
    private OffsetDateTime fechaPago;
    private BigDecimal cantidad;
    private EstadoPago estado;
    private Integer idReserva;
}