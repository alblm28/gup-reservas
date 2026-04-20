package io.github.alblm28.reserva_cabanas.dto.respuesta;

import lombok.Data;
import java.time.LocalDate;

@Data
public class HuespedRespuesta {

    private Integer idHuesped;
    private String nif;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private LocalDate fechaNacimiento;
    private String tel;
    private String email;
}
