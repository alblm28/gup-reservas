package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.Incluye;
import io.github.alblm28.reserva_cabanas.service.IncluyeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incluye")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class IncluyeController {

    private final IncluyeService incluyeService;


    @GetMapping
    public List<Incluye> listarTodos() {
        return incluyeService.obtenerTodos();
    }

    @GetMapping("/reserva/{idReserva}")
    public List<Incluye> listarPorReserva(@PathVariable Integer idReserva) {
        return incluyeService.obtenerPorReserva(idReserva);
    }

    @GetMapping("/{idReserva}/{idHuesped}")
    public Incluye buscarPorClave(
            @PathVariable Integer idReserva,
            @PathVariable Integer idHuesped) {
        return incluyeService.buscarPorClave(idReserva, idHuesped)
                .orElseThrow(() -> new RuntimeException("Relación no encontrada"));
    }

    @GetMapping("/existe/{idReserva}/{idHuesped}")
    public boolean verificarExiste(
            @PathVariable Integer idReserva,
            @PathVariable Integer idHuesped) {
        return incluyeService.yaEstaIncluido(idReserva, idHuesped);
    }


    @PostMapping
    public Incluye crear(@RequestBody Incluye incluye) {
        return incluyeService.crear(incluye);
    }

    @DeleteMapping("/{idReserva}/{idHuesped}")
    public void eliminar(
            @PathVariable Integer idReserva,
            @PathVariable Integer idHuesped) {
        incluyeService.eliminar(idReserva, idHuesped);
    }

    @DeleteMapping("/reserva/{idReserva}")
    public void eliminarPorReserva(@PathVariable Integer idReserva) {
        incluyeService.eliminarPorReserva(idReserva);
    }
}