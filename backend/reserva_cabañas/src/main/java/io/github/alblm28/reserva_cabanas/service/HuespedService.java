package io.github.alblm28.reserva_cabanas.service;

import io.github.alblm28.reserva_cabanas.model.Huesped;
import io.github.alblm28.reserva_cabanas.repository.HuespedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HuespedService {

    private final HuespedRepository huespedRepository;

    // Devuelve todos los huéspedes
    public List<Huesped> obtenerTodos() {
        return huespedRepository.findAll();
    }

    // Devuelve un huésped por id, lanza excepción si no existe
    public Huesped obtenerPorId(Integer id) {
        return huespedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Huésped no encontrado con id: " + id));
    }

    // Busca huésped por NIF
    public Optional<Huesped> buscarPorNif(String nif) {
        return huespedRepository.findByNif(nif);
    }

    // Verifica si un NIF ya existe
    public boolean nifExiste(String nif) {
        return huespedRepository.existsByNif(nif);
    }

    // Crea un huésped nuevo
    public Huesped crear(Huesped huesped) {
        if (huespedRepository.existsByNif(huesped.getNif())) {
            throw new RuntimeException("Ya existe un huésped con ese NIF: " + huesped.getNif());
        }
        return huespedRepository.save(huesped);
    }

    // Actualiza un huésped existente
    public Huesped actualizar(Integer id, Huesped datos) {
        Huesped huesped = obtenerPorId(id);

        // Actualizar solo los campos que vienen con valor
        if (datos.getNif() != null) {
            if (!huesped.getNif().equals(datos.getNif()) &&
                    huespedRepository.existsByNif(datos.getNif())) {
                throw new RuntimeException("El NIF ya está en uso por otro huésped");
            }
            huesped.setNif(datos.getNif());
        }
        if (datos.getNombre() != null) {
            huesped.setNombre(datos.getNombre());
        }
        if (datos.getApellido1() != null) {
            huesped.setApellido1(datos.getApellido1());
        }
        if (datos.getApellido2() != null) {
            huesped.setApellido2(datos.getApellido2());
        }
        if (datos.getFechaNacimiento() != null) {
            huesped.setFechaNacimiento(datos.getFechaNacimiento());
        }
        if (datos.getTel() != null) {
            huesped.setTel(datos.getTel());
        }
        if (datos.getEmail() != null) {
            huesped.setEmail(datos.getEmail());
        }

        return huespedRepository.save(huesped);
    }

    // Elimina un huésped
    public void eliminar(Integer id) {
        obtenerPorId(id); // lanza excepción si no existe
        huespedRepository.deleteById(id);
    }
}