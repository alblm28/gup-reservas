package io.github.alblm28.reserva_cabanas.dto.respuesta;

import lombok.Data;

@Data
public class FotosCabaniaRespuesta {

    private Integer idFoto;
    private String urlImagen;
    private Short orden;
}
