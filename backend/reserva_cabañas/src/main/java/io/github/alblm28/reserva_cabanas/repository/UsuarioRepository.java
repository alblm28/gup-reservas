package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;//metodos crud automaticos
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository  // marca interfaz como componente de spring
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // BUSCAR POR EMAIL
    Optional<Usuario> findByEmail(String email);
    //optional x si no existe
    //si existe email
    boolean existsByEmail(String email);

    //comprueba si tiene reserva activa en el momento
    @Query("SELECT r FROM Reserva r WHERE r.usuario.idUsuario = :idUsuario " +
            "AND r.estado = 'confirmada' " +
            "AND r.fechaInicio <= :ahora " +
            "AND r.fechaFin >= :ahora")
    List<Reserva> findReservasActivasByUsuario(
            @Param("idUsuario") Integer idUsuario,
            @Param("ahora") ZonedDateTime ahora
    );
}