package io.github.alblm28.reserva_cabanas.repository;

import io.github.alblm28.reserva_cabanas.model.EstadoPago;
import io.github.alblm28.reserva_cabanas.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {

    // los pagos q haya
    List<Pago> findByReservaIdReserva(Integer idReserva);
    //por transaccion externa
    Optional<Pago> findByIdTransaccionExterna(String idTransaccionExterna);
  //estado de pagos
    List<Pago> findByEstado(EstadoPago estado);
}