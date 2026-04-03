package io.github.alblm28.reserva_cabanas.controller;

import io.github.alblm28.reserva_cabanas.model.FotosCabania;
import io.github.alblm28.reserva_cabanas.service.FotosCabaniaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FotosCabaniaController {

    private final FotosCabaniaService fotosService;


    // GET /api/fotos → Lista todas las fotos
    @GetMapping
    public List<FotosCabania> listarTodas() {
        return fotosService.obtenerTodas();
    }

    // GET /api/fotos/{id} → Busca foto por ID
    @GetMapping("/{id}")
    public FotosCabania buscarPorId(@PathVariable Integer id) {
        return fotosService.obtenerPorId(id);
    }

    // GET /api/fotos/cabania/{idCabania} → Fotos de una cabaña (ordenadas)
    @GetMapping("/cabania/{idCabania}")
    public List<FotosCabania> buscarPorCabania(@PathVariable Integer idCabania) {
        return fotosService.obtenerPorCabania(idCabania);
    }


    // POST /api/fotos → Crea nueva foto
    @PostMapping
    public FotosCabania crear(@RequestBody FotosCabania foto) {
        return fotosService.crear(foto);
    }


    // PUT /api/fotos/{id} → Actualiza foto
    @PutMapping("/{id}")
    public FotosCabania actualizar(
            @PathVariable Integer id,
            @RequestBody FotosCabania foto) {
        return fotosService.actualizar(id, foto);
    }

    // DELETE /api/fotos/{id} → Elimina foto
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        fotosService.eliminar(id);
    }

    // DELETE /api/fotos/cabania/{idCabania} → Elimina todas las fotos de una cabaña
    @DeleteMapping("/cabania/{idCabania}")
    public void eliminarPorCabania(@PathVariable Integer idCabania) {
        fotosService.eliminarPorCabania(idCabania);
    }
}