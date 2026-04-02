package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.Incluye;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncluyeRepository extends JpaRepository<Incluye, Integer> {
    //ver los huespedes de una reserva
    List<Incluye> findByIdReserva(Integer idReserva);
    // borra los huespedes de una reserva
    void deleteByIdReserva(Integer idReserva);
}