package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.FotosCabania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FotosCabaniaRepository extends JpaRepository<FotosCabania, Integer> {
    //para ordenar en el carrusel
    List<FotosCabania> findByCabaniaIdCabaniaOrderByOrden(Integer idCabania);
}