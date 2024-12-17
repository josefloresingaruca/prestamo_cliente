package com.banco.clientes.dto;

import com.banco.clientes.model.EstadoPrestamo;

import java.time.LocalDate;

public record PrestamoUpdateDTO (String id,
                                 double monto,
                                 String clienteId,
                                 LocalDate fecha,
                                 EstadoPrestamo estado)
{
}
