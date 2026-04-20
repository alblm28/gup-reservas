package io.github.alblm28.reserva_cabanas.dto.respuesta;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRespuesta {

    private String token;         // JWT que el frontend guarda
    private UsuarioRespuesta usuario; // datos del usuario para mostrar en la UI
}