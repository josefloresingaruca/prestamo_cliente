package com.banco.clientes.service;

import com.banco.clientes.dto.*;

import java.util.List;

public interface PrestamoService {
    void crearPrestamo(PrestamoCreateDTO prestamoCreateDTO);

    List<PrestamoDTO> obtenerPrestamosActivos();

    void actualizarPrestamo(String id, PrestamoUpdateDTO prestamoUpdateDTO);

    void eliminarPrestamo(String id);

}
