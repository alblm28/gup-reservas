package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.Cabania;
import io.github.alblm28.reserva_cabanas.model.EstadoReserva;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import io.github.alblm28.reserva_cabanas.model.EstadoCabania;
@Repository
public interface CabaniaRepository extends JpaRepository<Cabania, Integer> {
    //por estado
    List<Cabania> findByEstado(EstadoCabania estado);
    //capacidad mayor o igual
    List<Cabania> findByCapacidadMaxGreaterThanEqual(Short capacidad);

    //verifica si está activa ahora
    @Query("SELECT CASE WHEN COUNT(r) > 0  THEN true ELSE false END " +
"FROM Reserva r WHERE r.cabania.idCabania = :idCabania " +
            "AND r.estado = :estado" +
            " AND r.fechaInicio <= :ahora " +
            "AND r.fechaFin >= :ahora")
    boolean isCabaniaEnUso(@Param("idCabania") Integer idCabania,
                           @Param("ahora") ZonedDateTime ahora,
                        @Param("estado") EstadoReserva estado);
}