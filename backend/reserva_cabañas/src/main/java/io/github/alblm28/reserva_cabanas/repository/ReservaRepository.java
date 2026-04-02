package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.EstadoReserva;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    // por usuario
    List<Reserva> findByUsuarioIdUsuario(Integer idUsuario);

    // por cabaña
    List<Reserva> findByCabaniaIdCabania(Integer idCabania);

    // por estado
    List<Reserva> findByEstado(EstadoReserva estado);

    // por rango de fechas
    List<Reserva> findByFechaInicioBetween(ZonedDateTime inicio, ZonedDateTime fin);

    //Verifica reservas solapadas
    @Query("SELECT r FROM Reserva r WHERE r.cabania.idCabania = :idCabania " +
            "AND r.estado NOT IN ('cancelada', 'completada') " +
            "AND r.fechaInicio < :fechaFin " +
            "AND r.fechaFin > :fechaInicio")

    List<Reserva> findReservasSolapadas(
            @Param("idCabania") Integer idCabania,
            @Param("fechaInicio") ZonedDateTime fechaInicio,
            @Param("fechaFin") ZonedDateTime fechaFin
    );

    //verifica si está activa ahora
    @Query("SELECT r FROM Reserva r WHERE r.cabania.idCabania = :idCabania " +
            "AND r.estado = 'confirmada' " +
            "AND r.fechaInicio <= :ahora " +
            "AND r.fechaFin >= :ahora")
    Optional<Reserva> findReservaActiva(
            @Param("idCabania") Integer idCabania,
            @Param("ahora") ZonedDateTime ahora,
             @Param("estado") EstadoReserva estado
    );
}