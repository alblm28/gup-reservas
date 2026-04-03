package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.Huesped;
import io.github.alblm28.reserva_cabanas.service.HuespedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/huespedes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class HuespedController {

    private final HuespedService huespedService;

    // GET /api/huespedes
    @GetMapping
    public List<Huesped> listarTodos() {
        return huespedService.obtenerTodos();
    }

    // GET /api/huespedes/{id} → Busca por ID
    @GetMapping("/{id}")
    public Huesped buscarPorId(@PathVariable Integer id) {
        return huespedService.obtenerPorId(id);
    }

    // GET /api/huespedes/nif/{nif} → Busca por NIF
    @GetMapping("/nif/{nif}")
    public Huesped buscarPorNif(@PathVariable String nif) {
        return huespedService.buscarPorNif(nif)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado con NIF: " + nif));
    }

    // GET /api/huespedes/existe/{nif} → Verifica si NIF existe
    @GetMapping("/existe/{nif}")
    public boolean verificarNifExiste(@PathVariable String nif) {
        return huespedService.nifExiste(nif);
    }


    // POST /api/huespedes → Crea nuevo huésped
    @PostMapping
    public Huesped crear(@RequestBody Huesped huesped) {
        return huespedService.crear(huesped);
    }

    // PUT /api/huespedes/{id} → Actualiza huésped
    @PutMapping("/{id}")
    public Huesped actualizar(
            @PathVariable Integer id,
            @RequestBody Huesped huesped) {
        return huespedService.actualizar(id, huesped);
    }


    // DELETE /api/huespedes/{id} → Elimina huésped
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        huespedService.eliminar(id);
    }
}