package com.banco.clientes.service;

import com.banco.clientes.dto.ClienteCreateDTO;
import com.banco.clientes.dto.ClienteDTO;
import com.banco.clientes.dto.ClienteUpdateDTO;

import java.util.List;

public interface ClienteService {
    void crearCliente(ClienteCreateDTO clienteCreateDTO);

    List<ClienteDTO> obtenerTodos();

    void actualizarCliente(String id, ClienteUpdateDTO clienteUpdateDTO);

    void eliminarCliente(String id);

    String realizarAccionPorTipo(String id);
}