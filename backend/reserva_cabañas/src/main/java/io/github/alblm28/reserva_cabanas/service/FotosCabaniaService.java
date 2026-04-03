package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.FotosCabania;
import io.github.alblm28.reserva_cabanas.repository.FotosCabaniaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FotosCabaniaService {

    private final FotosCabaniaRepository fotosRepository;

    // Devuelve todas las fotos
    public List<FotosCabania> obtenerTodas() {
        return fotosRepository.findAll();
    }

    // Devuelve una foto por id, lanza excepción si no existe
    public FotosCabania obtenerPorId(Integer id) {
        return fotosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Foto no encontrada con id: " + id));
    }

    // Devuelve fotos de una cabaña ordenadas por 'orden'
    public List<FotosCabania> obtenerPorCabania(Integer idCabania) {
        return fotosRepository.findByCabaniaIdCabaniaOrderByOrden(idCabania);
    }

    // Crea una foto nueva
    public FotosCabania crear(FotosCabania foto) {
        // Validación: orden no negativo
        if (foto.getOrden() != null && foto.getOrden() < 0) {
            throw new IllegalArgumentException("El orden no puede ser negativo");
        }
        return fotosRepository.save(foto);
    }

    // Actualiza una foto existente
    public FotosCabania actualizar(Integer id, FotosCabania datos) {
        FotosCabania foto = obtenerPorId(id);

        // Actualizar solo los campos que vienen con valor
        if (datos.getUrlImagen() != null) {
            foto.setUrlImagen(datos.getUrlImagen());
        }
        if (datos.getOrden() != null) {
            foto.setOrden(datos.getOrden());
        }
        // La relación con cabania normalmente no se cambia

        return fotosRepository.save(foto);
    }

    // Elimina una foto
    public void eliminar(Integer id) {
        obtenerPorId(id); // lanza excepción si no existe
        fotosRepository.deleteById(id);
    }

    // Elimina todas las fotos de una cabaña
    public void eliminarPorCabania(Integer idCabania) {
        List<FotosCabania> fotos = obtenerPorCabania(idCabania);
        fotosRepository.deleteAll(fotos);
    }
}