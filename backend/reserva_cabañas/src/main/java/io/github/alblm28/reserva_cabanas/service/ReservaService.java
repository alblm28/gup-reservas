package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.Cabania;
import io.github.alblm28.reserva_cabanas.model.EstadoCabania;
import io.github.alblm28.reserva_cabanas.model.EstadoReserva;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.model.Usuario;
import io.github.alblm28.reserva_cabanas.repository.CabaniaRepository;
import io.github.alblm28.reserva_cabanas.repository.ReservaRepository;
import io.github.alblm28.reserva_cabanas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final CabaniaRepository cabaniaRepository;
    private final UsuarioRepository usuarioRepository;


    public List<Reserva> obtenerTodas() {
        return reservaRepository.findAll();
    }

    public Reserva obtenerPorId(Integer id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    public List<Reserva> obtenerPorUsuario(Integer idUsuario) {
        return reservaRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Reserva> obtenerPorCabania(Integer idCabania) {
        return reservaRepository.findByCabaniaIdCabania(idCabania);
    }

    public List<Reserva> obtenerPorEstado(EstadoReserva estado) {
        return reservaRepository.findByEstado(estado);
    }

    public List<Reserva> obtenerPorRangoFechas(ZonedDateTime inicio, ZonedDateTime fin) {
        return reservaRepository.findByFechaInicioBetween(inicio, fin);
    }

    // Verifica si hay solapamiento con reservas existentes
    public boolean haySolapamiento(Integer idCabania, ZonedDateTime inicio, ZonedDateTime fin) {
        List<Reserva> solapadas = reservaRepository.findReservasSolapadas(idCabania, inicio, fin);
        return !solapadas.isEmpty();
    }

    // Calcula precio: precioNoche × número de noches
    public BigDecimal calcularPrecio(Cabania cabania, ZonedDateTime inicio, ZonedDateTime fin) {
        long noches = ChronoUnit.DAYS.between(inicio.toLocalDate(), fin.toLocalDate());
        if (noches <= 0) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la de inicio");
        }
        return cabania.getPrecioNoche().multiply(BigDecimal.valueOf(noches));
    }

    public Reserva crear(Reserva reserva) {
        // 1. Validar que la cabaña existe y está disponible
        Cabania cabania = cabaniaRepository.findById(reserva.getCabania().getIdCabania())
                .orElseThrow(() -> new RuntimeException("Cabaña no encontrada"));

        if (cabania.getEstado() != EstadoCabania.disponible) {
            throw new IllegalStateException("La cabaña no está disponible");
        }

        // 2. Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(reserva.getUsuario().getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 3. Verificar solapamiento de fechas
        if (haySolapamiento(cabania.getIdCabania(), reserva.getFechaInicio(), reserva.getFechaFin())) {
            throw new IllegalStateException("Ya existe una reserva en esas fechas");
        }

        // 4. Calcular precio automático (sobrescribe si viene manual)
        reserva.setPrecioFinal(calcularPrecio(cabania, reserva.getFechaInicio(), reserva.getFechaFin()));

        // 5. Estado por defecto
        if (reserva.getEstado() == null) {
            reserva.setEstado(EstadoReserva.bloqueada);
        }

        // 6. Asignar relaciones
        reserva.setCabania(cabania);
        reserva.setUsuario(usuario);

        return reservaRepository.save(reserva);
    }

    public Reserva actualizar(Integer id, Reserva datos) {
        Reserva existente = obtenerPorId(id);

        // Solo se puede modificar si está 'pendiente'
        if (existente.getEstado() != EstadoReserva.bloqueada) {
            throw new IllegalStateException("Solo se pueden modificar reservas pendientes");
        }

        if (datos.getFechaInicio() != null) {
            existente.setFechaInicio(datos.getFechaInicio());
        }
        if (datos.getFechaFin() != null) {
            existente.setFechaFin(datos.getFechaFin());
        }
        if (datos.getNumHuespedes() != null) {
            existente.setNumHuespedes(datos.getNumHuespedes());
        }
        // Estado y precio no se actualizan manualmente aquí

        return reservaRepository.save(existente);
    }

    // Cambiar estado de una reserva
    public Reserva cambiarEstado(Integer id, EstadoReserva nuevoEstado) {
        Reserva reserva = obtenerPorId(id);
        reserva.setEstado(nuevoEstado);
        return reservaRepository.save(reserva);
    }

    public void eliminar(Integer id) {
        Reserva reserva = obtenerPorId(id);
        // Solo se puede eliminar si está 'pendiente' o 'cancelada'
        if (reserva.getEstado() == EstadoReserva.confirmada ||
                reserva.getEstado() == EstadoReserva.completada) {
            throw new IllegalStateException("No se puede eliminar una reserva " + reserva.getEstado());
        }
        reservaRepository.deleteById(id);
    }
}