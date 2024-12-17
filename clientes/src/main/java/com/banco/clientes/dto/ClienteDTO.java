package com.banco.clientes.dto;

import com.banco.clientes.model.TipoCliente;

public record ClienteDTO(
        String id,
        String nombre,
        String email,
        int edad,
        TipoCliente tipoCliente
) {}