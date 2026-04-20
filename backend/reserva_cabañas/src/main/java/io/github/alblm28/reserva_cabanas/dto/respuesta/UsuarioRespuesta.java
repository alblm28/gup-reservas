package io.github.alblm28.reserva_cabanas.dto.respuesta;

import io.github.alblm28.reserva_cabanas.model.*;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class UsuarioRespuesta {

    private Integer idUsuario;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String email;
    private String tel;
    private RolUsuario rol;
    private OffsetDateTime fechaCreacion;
    // contraseña NO
}