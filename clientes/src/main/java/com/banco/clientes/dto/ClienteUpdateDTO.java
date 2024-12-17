package com.banco.clientes.dto;

import com.banco.clientes.model.TipoCliente;

public record ClienteUpdateDTO(
        String id,
        String nombre,
        String email,
        int edad,
        TipoCliente tipoCliente
) {}