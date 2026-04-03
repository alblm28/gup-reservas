package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.EstadoPago;
import io.github.alblm28.reserva_cabanas.model.Pago;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.repository.PagoRepository;
import io.github.alblm28.reserva_cabanas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final ReservaRepository reservaRepository;

    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPorId(Integer id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con id: " + id));
    }

    public List<Pago> obtenerPorReserva(Integer idReserva) {
        return pagoRepository.findByReservaIdReserva(idReserva);
    }

    public Optional<Pago> buscarPorTransaccion(String idTransaccion) {
        return pagoRepository.findByIdTransaccionExterna(idTransaccion);
    }

    public List<Pago> obtenerPorEstado(EstadoPago estado) {
        return pagoRepository.findByEstado(estado);
    }


    public Pago crear(Pago pago) {
        // Validar que la reserva existe
        Reserva reserva = reservaRepository.findById(pago.getReserva().getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        //Validar cantidad positiva
        if (pago.getCantidad() == null || pago.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        // Estado por defecto
        if (pago.getEstado() == null) {
            pago.setEstado(EstadoPago.pendiente);
        }

        // Fecha de pago si es completado
        if (pago.getEstado() == EstadoPago.completado && pago.getFechaPago() == null) {
            pago.setFechaPago(ZonedDateTime.now());
        }

        // Asignar relación
        pago.setReserva(reserva);

        return pagoRepository.save(pago);
    }

    public Pago actualizar(Integer id, Pago datos) {
        Pago existente = obtenerPorId(id);

        // Solo se puede modificar si está pendiente o fallido
        if (existente.getEstado() == EstadoPago.completado ||
                existente.getEstado() == EstadoPago.reembolsado ||
                existente.getEstado() == EstadoPago.cancelado) {
            throw new IllegalStateException("No se puede modificar un pago " + existente.getEstado());
        }

        if (datos.getCantidad() != null) {
            existente.setCantidad(datos.getCantidad());
        }
        if (datos.getEstado() != null) {
            existente.setEstado(datos.getEstado());
            // Si cambia a completado, registrar fecha
            if (datos.getEstado() == EstadoPago.completado && existente.getFechaPago() == null) {
                existente.setFechaPago(ZonedDateTime.now());
            }
        }
        if (datos.getIdTransaccionExterna() != null) {
            existente.setIdTransaccionExterna(datos.getIdTransaccionExterna());
        }

        return pagoRepository.save(existente);
    }

    // Cambiar estado de un pago
    public Pago cambiarEstado(Integer id, EstadoPago nuevoEstado) {
        Pago pago = obtenerPorId(id);

        // Validaciones de transición de estados
        if (pago.getEstado() == EstadoPago.completado && nuevoEstado != EstadoPago.reembolsado) {
            throw new IllegalStateException("Un pago completado solo puede ser reembolsado");
        }
        if (pago.getEstado() == EstadoPago.reembolsado) {
            throw new IllegalStateException("Un pago reembolsado no puede modificarse");
        }

        pago.setEstado(nuevoEstado);

        // Si se completa, registrar fecha
        if (nuevoEstado == EstadoPago.completado && pago.getFechaPago() == null) {
            pago.setFechaPago(ZonedDateTime.now());
        }

        return pagoRepository.save(pago);
    }

    public void eliminar(Integer id) {
        Pago pago = obtenerPorId(id);
        // Solo se puede eliminar si está pendiente o fallido
        if (pago.getEstado() == EstadoPago.completado ||
                pago.getEstado() == EstadoPago.reembolsado) {
            throw new IllegalStateException("No se puede eliminar un pago " + pago.getEstado());
        }
        pagoRepository.deleteById(id);
    }
}