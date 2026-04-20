package io.github.alblm28.reserva_cabanas.dto.mapper;

import io.github.alblm28.reserva_cabanas.dto.peticion.HuespedPeticion;
import io.github.alblm28.reserva_cabanas.dto.respuesta.HuespedRespuesta;
import io.github.alblm28.reserva_cabanas.model.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMapper {

    public HuespedRespuesta aRespuesta(Huesped huesped) {
        HuespedRespuesta dto = new HuespedRespuesta();
        dto.setIdHuesped(huesped.getIdHuesped());
        dto.setNif(huesped.getNif());
        dto.setNombre(huesped.getNombre());
        dto.setApellido1(huesped.getApellido1());
        dto.setApellido2(huesped.getApellido2());
        dto.setFechaNacimiento(huesped.getFechaNacimiento());
        dto.setTel(huesped.getTel());
        dto.setEmail(huesped.getEmail());
        return dto;
    }

    public Huesped aEntidad(HuespedPeticion dto) {
        Huesped huesped = new Huesped();
        huesped.setNif(dto.getNif());
        huesped.setNombre(dto.getNombre());
        huesped.setApellido1(dto.getApellido1());
        huesped.setApellido2(dto.getApellido2());
        huesped.setFechaNacimiento(dto.getFechaNacimiento());
        huesped.setTel(dto.getTel());
        huesped.setEmail(dto.getEmail());
        return huesped;
    }
}