package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.model.Usuario;
import io.github.alblm28.reserva_cabanas.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;


    // GET /api/usuarios → Lista todos
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.obtenerTodos();
    }

    // GET /api/usuarios/{id} → Busca por ID
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    // GET /api/usuarios/email/{email} → Busca por email
    @GetMapping("/email/{email}")
    public Usuario buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    // GET /api/usuarios/existe/{email} → Verifica si email existe
    @GetMapping("/existe/{email}")
    public boolean verificarEmailExiste(@PathVariable String email) {
        return usuarioService.emailExiste(email);
    }

    // GET /api/usuarios/{id}/reservas-activas → Reservas activas ahora
    @GetMapping("/{id}/reservas-activas")
    public List<Reserva> obtenerReservasActivas(@PathVariable Integer id) {
        return usuarioService.obtenerReservasActivas(id);
    }

    // POST /api/usuarios → Crea nuevo usuario
    @PostMapping
    public Usuario crear(@RequestBody Usuario usuario) {
        return usuarioService.crear(usuario);
    }

    // PUT /api/usuarios/{id} → Actualiza usuario
    @PutMapping("/{id}")
    public Usuario actualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuario) {
        return usuarioService.actualizar(id, usuario);
    }


    // DELETE /api/usuarios/{id} → Elimina usuario
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        usuarioService.eliminar(id);
    }
}
