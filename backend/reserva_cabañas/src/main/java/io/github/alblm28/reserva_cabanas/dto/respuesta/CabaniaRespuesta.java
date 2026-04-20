package io.github.alblm28.reserva_cabanas.dto.respuesta;

import io.github.alblm28.reserva_cabanas.model.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CabaniaRespuesta {

    private Integer idCabania;
    private String nombreCabania;
    private String descripcion;
    private Short capacidadMax;
    private BigDecimal precioNoche;
    private EstadoCabania estado;
    private TipoVentilacion ventilacion;
    private TipoCama cama;
    private Short enchufes;
    private Short numBanios;
    private List<FotosCabaniaRespuesta> fotos;
}
