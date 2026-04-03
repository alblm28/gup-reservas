package io.github.alblm28.reserva_cabanas.model;

import java.io.Serializable;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncluyeId implements Serializable {
    private Integer idReserva;
    private Integer idHuesped;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncluyeId that)) return false;
        return Objects.equals(idReserva, that.idReserva) &&
                Objects.equals(idHuesped, that.idHuesped);
    }
    @Override public int hashCode() {
        return Objects.hash(idReserva, idHuesped);
    }
}