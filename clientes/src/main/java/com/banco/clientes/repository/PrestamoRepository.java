package com.banco.clientes.repository;

import com.banco.clientes.exception.PrestamoNotFoundException;
import com.banco.clientes.model.Prestamo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PrestamoRepository {
    private final List<Prestamo> prestamos = new ArrayList<>();

    public List<Prestamo> findAll() {
        return List.copyOf(prestamos);
    }

    public Optional<Prestamo> findById(String id) {
        return prestamos.stream()
                .filter(prestamo -> prestamo.id().equals(id))
                .findFirst();
    }

    public void save(Prestamo prestamo) {
        prestamos.add(prestamo);
    }

    public void update(String id, Prestamo updatedPrestamo) {
        var index = prestamos.stream()
                .map(Prestamo::id)
                .toList()
                .indexOf(id);

        if (index >= 0) {
            prestamos.set(index, updatedPrestamo );
        } else {
            throw new PrestamoNotFoundException("Prestamo con ID " + id + " no encontrado");
        }
    }

    public void delete(String id) {
        boolean eliminado = prestamos.removeIf(prestamo -> prestamo.id().equals(id));

        if (!eliminado) {
            throw new PrestamoNotFoundException("Prestamo con ID " + id + " no encontrado");
        }
    }
}
