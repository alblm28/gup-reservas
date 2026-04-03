package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.Incluye;
import io.github.alblm28.reserva_cabanas.model.IncluyeId;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.model.Huesped;
import io.github.alblm28.reserva_cabanas.repository.IncluyeRepository;
import io.github.alblm28.reserva_cabanas.repository.ReservaRepository;
import io.github.alblm28.reserva_cabanas.repository.HuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IncluyeService {

    private final IncluyeRepository incluyeRepository;
    private final ReservaRepository reservaRepository;
    private final HuespedRepository huespedRepository;


    public List<Incluye> obtenerTodos() {
        return incluyeRepository.findAll();
    }

    public List<Incluye> obtenerPorReserva(Integer idReserva) {
        return incluyeRepository.findByIdReserva(idReserva);
    }

    public Optional<Incluye> buscarPorClave(Integer idReserva, Integer idHuesped) {
        IncluyeId id = new IncluyeId(idReserva, idHuesped);
        return incluyeRepository.findById(id);
    }

    public boolean yaEstaIncluido(Integer idReserva, Integer idHuesped) {
        return buscarPorClave(idReserva, idHuesped).isPresent();
    }


    public Incluye crear(Incluye incluye) {
        // Validar que la reserva existe
        Reserva reserva = reservaRepository.findById(incluye.getIdReserva())
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        // Validar que el huésped existe
        Huesped huesped = huespedRepository.findById(incluye.getIdHuesped())
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado"));

        // Verificar que no esté ya incluido
        if (yaEstaIncluido(incluye.getIdReserva(), incluye.getIdHuesped())) {
            throw new IllegalStateException("El huésped ya está incluido en esta reserva");
        }

        // Asignar relaciones
        incluye.setReserva(reserva);
        incluye.setHuesped(huesped);

        return incluyeRepository.save(incluye);
    }

    public void eliminar(Integer idReserva, Integer idHuesped) {
        if (!yaEstaIncluido(idReserva, idHuesped)) {
            throw new RuntimeException("El huésped no está incluido en esta reserva");
        }
        IncluyeId id = new IncluyeId(idReserva, idHuesped);
        incluyeRepository.deleteById(id);
    }

    public void eliminarPorReserva(Integer idReserva) {
        incluyeRepository.deleteByIdReserva(idReserva);
    }
}