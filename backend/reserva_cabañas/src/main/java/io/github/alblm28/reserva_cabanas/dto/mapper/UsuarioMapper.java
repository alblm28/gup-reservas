package io.github.alblm28.reserva_cabanas.dto.mapper;

import io.github.alblm28.reserva_cabanas.dto.peticion.UsuarioPeticion;
import io.github.alblm28.reserva_cabanas.dto.respuesta.UsuarioRespuesta;
import io.github.alblm28.reserva_cabanas.model.RolUsuario;
import io.github.alblm28.reserva_cabanas.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    // Entidad → DTO respuesta (para devolver al frontend)
    public UsuarioRespuesta aRespuesta(Usuario usuario) {
        UsuarioRespuesta dto = new UsuarioRespuesta();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido1(usuario.getApellido1());
        dto.setApellido2(usuario.getApellido2());
        dto.setEmail(usuario.getEmail());
        dto.setTel(usuario.getTel());
        dto.setRol(usuario.getRol());
        dto.setFechaCreacion(usuario.getFechaCreacion().toOffsetDateTime());
        return dto;
    }

    // DTO petición → Entidad (para guardar en BD)
    // La contraseña ya viene hasheada desde el Service
    public Usuario aEntidad(UsuarioPeticion dto, String contraseniaHasheada) {
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido1(dto.getApellido1());
        usuario.setApellido2(dto.getApellido2());
        usuario.setEmail(dto.getEmail());
        usuario.setTel(dto.getTel());
        usuario.setContrasenia(contraseniaHasheada);
        usuario.setRol(RolUsuario.voluntario);
        return usuario;
    }
}