package io.github.alblm28.reserva_cabanas.dto.mapper;

import io.github.alblm28.reserva_cabanas.dto.respuesta.PagoRespuesta;
import io.github.alblm28.reserva_cabanas.model.Pago;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    public PagoRespuesta aRespuesta(Pago pago) {
        PagoRespuesta dto = new PagoRespuesta();
        dto.setIdPago(pago.getIdPago());
        dto.setIdTransaccionExterna(pago.getIdTransaccionExterna());
        dto.setFechaPago(pago.getFechaPago());
        dto.setCantidad(pago.getCantidad());
        dto.setEstado(pago.getEstado());
        dto.setIdReserva(pago.getReserva().getIdReserva());
        return dto;
    }
}