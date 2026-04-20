package io.github.alblm28.reserva_cabanas.dto.mapper;

import io.github.alblm28.reserva_cabanas.dto.respuesta.ReservaRespuesta;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservaMapper {

    private final HuespedMapper huespedMapper;
    private final PagoMapper    pagoMapper;

    public ReservaRespuesta aRespuesta(Reserva reserva) {
        ReservaRespuesta dto = new ReservaRespuesta();
        dto.setIdReserva(reserva.getIdReserva());
        dto.setPrecioFinal(reserva.getPrecioFinal());
        dto.setNumHuespedes(reserva.getNumHuespedes());
        dto.setEstado(reserva.getEstado());
        dto.setFechaCreacion(reserva.getFechaCreacion().toOffsetDateTime());
        dto.setFechaInicio(reserva.getFechaInicio().toOffsetDateTime());
        dto.setFechaFin(reserva.getFechaFin().toOffsetDateTime());
        dto.setIdCabania(reserva.getCabania().getIdCabania());
        dto.setNombreCabania(reserva.getCabania().getNombreCabania());

        if (reserva.getPago() != null) {
            dto.setPago(pagoMapper.aRespuesta(reserva.getPago()));
        }

        return dto;
    }
}