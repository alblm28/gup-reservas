package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.Cabania;
import io.github.alblm28.reserva_cabanas.model.EstadoCabania;
import io.github.alblm28.reserva_cabanas.service.CabaniaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cabanias")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CabaniaController {

    private final CabaniaService cabaniaService;

    // GET http://localhost:8080/api/cabanas
    @GetMapping
    public ResponseEntity<List<Cabania>> obtenerTodas() {
        return ResponseEntity.ok(cabaniaService.obtenerTodas());
    }

    // GET http://localhost:8080/api/cabanas/1
    @GetMapping("/{id}")
    public ResponseEntity<Cabania> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(cabaniaService.obtenerPorId(id));
    }

    // GET http://localhost:8080/api/cabanas/estado/disponible
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cabania>> obtenerPorEstado(@PathVariable EstadoCabania estado) {
        return ResponseEntity.ok(cabaniaService.obtenerPorEstado(estado));
    }

    // GET http://localhost:8080/api/cabanas/capacidad/4
    @GetMapping("/capacidad/{capacidad}")
    public ResponseEntity<List<Cabania>> obtenerPorCapacidad(@PathVariable Short capacidad) {
        return ResponseEntity.ok(cabaniaService.obtenerPorCapacidad(capacidad));
    }

    // GET http://localhost:8080/api/cabanas/1/en-uso
    @GetMapping("/{id}/en-uso")
    public ResponseEntity<Boolean> estaEnUsoAhora(@PathVariable Integer id) {
        return ResponseEntity.ok(cabaniaService.estaEnUsoAhora(id));
    }

    // POST http://localhost:8080/api/cabanas
    @PostMapping
    public ResponseEntity<Cabania> crear(@RequestBody Cabania cabania) {
        return ResponseEntity.status(201).body(cabaniaService.crear(cabania));
    }

    // PUT http://localhost:8080/api/cabanas/1
    @PutMapping("/{id}")
    public ResponseEntity<Cabania> actualizar(@PathVariable Integer id,
                                              @RequestBody Cabania datos) {
        return ResponseEntity.ok(cabaniaService.actualizar(id, datos));
    }

    // DELETE http://localhost:8080/api/cabanas/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        cabaniaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}