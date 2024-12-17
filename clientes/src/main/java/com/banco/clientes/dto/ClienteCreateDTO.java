package com.banco.clientes.dto;

import com.banco.clientes.model.TipoCliente;

public record ClienteCreateDTO(
        String nombre,
        String email,
        int edad,
        TipoCliente tipoCliente
) {}