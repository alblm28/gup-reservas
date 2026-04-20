package io.github.alblm28.reserva_cabanas.dto.respuesta;

import io.github.alblm28.reserva_cabanas.model.EstadoReserva;
import lombok.Data;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Data
public class ReservaRespuesta {

    private Integer idReserva;
    private BigDecimal precioFinal;
    private Short numHuespedes;
    private EstadoReserva estado;
    private OffsetDateTime fechaCreacion;
    private OffsetDateTime fechaInicio;
    private OffsetDateTime fechaFin;

    // Se incluye el nombre de la cabaña para mostrarlo directamente
    // en el listado de reservas sin otra llamada al backend
    private Integer idCabania;
    private String nombreCabania;

    private List<HuespedRespuesta> huespedes;
    private PagoRespuesta pago; // null si aún no hay pago
}