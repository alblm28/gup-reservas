package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Integer> {
   //por nif
    Optional<Huesped> findByNif(String nif);
    //si nif existe
    boolean existsByNif(String nif);
}