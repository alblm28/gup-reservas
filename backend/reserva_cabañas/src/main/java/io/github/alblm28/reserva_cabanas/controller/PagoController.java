package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.EstadoPago;
import io.github.alblm28.reserva_cabanas.model.Pago;
import io.github.alblm28.reserva_cabanas.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public List<Pago> listarTodos() {
        return pagoService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Pago buscarPorId(@PathVariable Integer id) {
        return pagoService.obtenerPorId(id);
    }

    @GetMapping("/reserva/{idReserva}")
    public List<Pago> buscarPorReserva(@PathVariable Integer idReserva) {
        return pagoService.obtenerPorReserva(idReserva);
    }

    @GetMapping("/transaccion/{idTransaccion}")
    public Pago buscarPorTransaccion(@PathVariable String idTransaccion) {
        return pagoService.buscarPorTransaccion(idTransaccion)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado con transacción: " + idTransaccion));
    }

    @GetMapping("/estado/{estado}")
    public List<Pago> buscarPorEstado(@PathVariable EstadoPago estado) {
        return pagoService.obtenerPorEstado(estado);
    }

    @PostMapping
    public Pago crear(@RequestBody Pago pago) {
        return pagoService.crear(pago);
    }

    @PutMapping("/{id}")
    public Pago actualizar(
            @PathVariable Integer id,
            @RequestBody Pago pago) {
        return pagoService.actualizar(id, pago);
    }

    @PatchMapping("/{id}/estado")
    public Pago cambiarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoPago estado) {
        return pagoService.cambiarEstado(id, estado);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
    }
}