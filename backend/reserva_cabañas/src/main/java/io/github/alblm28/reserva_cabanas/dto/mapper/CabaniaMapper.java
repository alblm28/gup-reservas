package io.github.alblm28.reserva_cabanas.dto.mapper;

import io.github.alblm28.reserva_cabanas.dto.peticion.CabaniaPeticion;
import io.github.alblm28.reserva_cabanas.dto.respuesta.CabaniaRespuesta;
import io.github.alblm28.reserva_cabanas.dto.respuesta.FotosCabaniaRespuesta;
import io.github.alblm28.reserva_cabanas.model.Cabania;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CabaniaMapper {

    public CabaniaRespuesta aRespuesta(Cabania cabania) {
        CabaniaRespuesta dto = new CabaniaRespuesta();
        dto.setIdCabania(cabania.getIdCabania());
        dto.setNombreCabania(cabania.getNombreCabania());
        dto.setDescripcion(cabania.getDescripcion());
        dto.setCapacidadMax(cabania.getCapacidadMax());
        dto.setPrecioNoche(cabania.getPrecioNoche());
        dto.setEstado(cabania.getEstado());
        dto.setVentilacion(cabania.getVentilacion());
        dto.setCama(cabania.getCama());
        dto.setEnchufes(cabania.getEnchufes());
        dto.setNumBanios(cabania.getNumBanios());

        if (cabania.getFotos() != null) {
            List<FotosCabaniaRespuesta> fotos = cabania.getFotos().stream()
                    .map(f -> {
                        FotosCabaniaRespuesta fDto = new FotosCabaniaRespuesta();
                        fDto.setIdFoto(f.getIdFoto());
                        fDto.setUrlImagen(f.getUrlImagen());
                        fDto.setOrden(f.getOrden());
                        return fDto;
                    }).toList();
            dto.setFotos(fotos);
        }

        return dto;
    }

    public Cabania aEntidad(CabaniaPeticion dto) {
        Cabania cabania = new Cabania();
        cabania.setNombreCabania(dto.getNombreCabania());
        cabania.setDescripcion(dto.getDescripcion());
        cabania.setCapacidadMax(dto.getCapacidadMax());
        cabania.setPrecioNoche(dto.getPrecioNoche());
        cabania.setEstado(dto.getEstado());
        cabania.setVentilacion(dto.getVentilacion());
        cabania.setCama(dto.getCama());
        cabania.setEnchufes(dto.getEnchufes());
        cabania.setNumBanios(dto.getNumBanios());
        return cabania;
    }
}