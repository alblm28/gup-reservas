package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.Cabania;
import io.github.alblm28.reserva_cabanas.model.EstadoCabania;
import io.github.alblm28.reserva_cabanas.model.EstadoReserva;
import io.github.alblm28.reserva_cabanas.repository.CabaniaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CabaniaService {

    private final CabaniaRepository cabaniaRepository;

    // Devuelve todas las cabañas
    public List<Cabania> obtenerTodas() {
        return cabaniaRepository.findAll();
    }

    // Devuelve una cabaña por id
    public Cabania obtenerPorId(Integer id) {
        return cabaniaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cabaña no encontrada con id: " + id));
    }

    // Filtra por estado
    public List<Cabania> obtenerPorEstado(EstadoCabania estado) {
        return cabaniaRepository.findByEstado(estado);
    }

    // Filtra por capacidad mínima
    public List<Cabania> obtenerPorCapacidad(Short capacidad) {
        return cabaniaRepository.findByCapacidadMaxGreaterThanEqual(capacidad);
    }

    // Comprueba si está ocupada ahora mismo
    public boolean estaEnUsoAhora(Integer id) {
        return cabaniaRepository.isCabaniaEnUso(
                id,
                ZonedDateTime.now(),
                EstadoReserva.confirmada
        );
    }

    // Crea una cabaña nueva
    public Cabania crear(Cabania cabania) {
        return cabaniaRepository.save(cabania);
    }

    // Actualiza una cabaña existente
    public Cabania actualizar(Integer id, Cabania datos) {
        Cabania cabania = obtenerPorId(id);
        cabania.setNombreCabania(datos.getNombreCabania());
        cabania.setDescripcion(datos.getDescripcion());
        cabania.setCapacidadMax(datos.getCapacidadMax());
        cabania.setPrecioNoche(datos.getPrecioNoche());
        cabania.setEstado(datos.getEstado());
        cabania.setVentilacion(datos.getVentilacion());
        cabania.setCama(datos.getCama());
        cabania.setEnchufes(datos.getEnchufes());
        cabania.setNumBanios(datos.getNumBanios());
        return cabaniaRepository.save(cabania);
    }

    // Elimina una cabaña
    public void eliminar(Integer id) {
        obtenerPorId(id);
        cabaniaRepository.deleteById(id);
    }
}