package io.github.alblm28.reserva_cabanas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable; //para la id

@Entity
@Table(name = "incluye")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Incluye implements Serializable {

   //ID compuesta x reserva y huesped
    @Id
    @Column(name = "id_reserva")
    private Integer idReserva;

    @Id
    @Column(name = "id_huesped")
    private Integer idHuesped;

   //relacion reserva
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false, insertable = false, updatable = false)
    // evita conflictos id
    private Reserva reserva;

   //relacion huesped
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_huesped", nullable = false, insertable = false, updatable = false)
    private Huesped huesped;
}