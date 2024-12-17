package com.banco.clientes.repository;

import com.banco.clientes.exception.ClienteNotFoundException;
import com.banco.clientes.model.Cliente;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class ClienteRepository {
    private final List<Cliente> clientes = new ArrayList<>();

    public List<Cliente> findAll() {
        return List.copyOf(clientes);
    }

    public Optional<Cliente> findById(String id) {
        return clientes.stream()
                .filter(cliente -> cliente.id().equals(id))
                .findFirst();
    }

    public void save(Cliente cliente) {
        clientes.add(cliente);
    }

    public void update(String id, Cliente updatedCliente) {
        var index = clientes.stream()
                .map(Cliente::id)
                .toList()
                .indexOf(id);

        if (index >= 0) {
            clientes.set(index, updatedCliente);
        } else {
            throw new ClienteNotFoundException("Cliente con ID " + id + " no encontrado");
        }
    }

    public void delete(String id) {
        boolean eliminado = clientes.removeIf(cliente -> cliente.id().equals(id));

        if (!eliminado) {
            throw new ClienteNotFoundException("Cliente con ID " + id + " no encontrado");
        }
    }
}