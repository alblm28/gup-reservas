package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.EstadoReserva;
import io.github.alblm28.reserva_cabanas.model.Reserva;
import io.github.alblm28.reserva_cabanas.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReservaController {

    private final ReservaService reservaService;


    //  GET - Consultas
    @GetMapping
    public List<Reserva> listarTodas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Reserva buscarPorId(@PathVariable Integer id) {
        return reservaService.obtenerPorId(id);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Reserva> buscarPorUsuario(@PathVariable Integer idUsuario) {
        return reservaService.obtenerPorUsuario(idUsuario);
    }

    @GetMapping("/cabania/{idCabania}")
    public List<Reserva> buscarPorCabania(@PathVariable Integer idCabania) {
        return reservaService.obtenerPorCabania(idCabania);
    }

    @GetMapping("/estado/{estado}")
    public List<Reserva> buscarPorEstado(@PathVariable EstadoReserva estado) {
        return reservaService.obtenerPorEstado(estado);
    }

    @GetMapping("/rango")
    public List<Reserva> buscarPorRango(
            @RequestParam ZonedDateTime inicio,
            @RequestParam ZonedDateTime fin) {
        return reservaService.obtenerPorRangoFechas(inicio, fin);
    }


    //  POST - Crear
    @PostMapping
    public Reserva crear(@RequestBody Reserva reserva) {
        return reservaService.crear(reserva);
    }

    //  PUT Actualizar
    @PutMapping("/{id}")
    public Reserva actualizar(
            @PathVariable Integer id,
            @RequestBody Reserva reserva) {
        return reservaService.actualizar(id, reserva);
    }


    //  PATCH  Cambiar estado

    @PatchMapping("/{id}/estado")
    public Reserva cambiarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoReserva estado) {
        return reservaService.cambiarEstado(id, estado);
    }

    //DELETE eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        reservaService.eliminar(id);
    }
}