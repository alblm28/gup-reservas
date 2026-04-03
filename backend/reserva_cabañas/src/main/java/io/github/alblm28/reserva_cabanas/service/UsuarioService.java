package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.model.Usuario;
import io.github.alblm28.reserva_cabanas.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    // Devuelve todos los usuarios
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // Devuelve un usuario por id, lanza excepción si no existe
    public Usuario obtenerPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // Busca usuario por email
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verifica si un email existe
    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Obtiene reservas activas de un usuario (ahora mismo)
    public List<Reserva> obtenerReservasActivas(Integer idUsuario) {
        return usuarioRepository.findReservasActivasByUsuario(idUsuario, ZonedDateTime.now());
    }


    // Crea un usuario nuevo
    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con ese email: " + usuario.getEmail());
        }
        return usuarioRepository.save(usuario);
    }

    // Actualiza un usuario existente
    public Usuario actualizar(Integer id, Usuario datos) {
        Usuario usuario = obtenerPorId(id);

        // Actualizar solo los campos que vienen con valor
        if (datos.getEmail() != null) {
            if (!usuario.getEmail().equals(datos.getEmail()) &&
                    usuarioRepository.existsByEmail(datos.getEmail())) {
                throw new RuntimeException("El email ya está en uso por otro usuario");
            }
            usuario.setEmail(datos.getEmail());
        }
        if (datos.getContrasenia() != null) {
            usuario.setContrasenia(datos.getContrasenia());
        }
        if (datos.getNombre() != null) {
            usuario.setNombre(datos.getNombre());
        }


        return usuarioRepository.save(usuario);
    }

    // Elimina un usuario
    public void eliminar(Integer id) {
        obtenerPorId(id); // lanza excepción si no existe
        usuarioRepository.deleteById(id);
    }
}