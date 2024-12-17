package com.banco.clientes.controller;

import com.banco.clientes.dto.*;
import com.banco.clientes.service.PrestamoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de préstamos.
 */
@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService service;

    /**
     * Crear un nuevo préstamo.
     *
     * @param prestamoCreateDTO Datos del préstamo a crear.
     * @return Respuesta HTTP con estado 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Void> crearPrestamo(@RequestBody PrestamoCreateDTO prestamoCreateDTO) {
        service.crearPrestamo(prestamoCreateDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtener la lista de préstamos activos.
     *
     * @return Lista de préstamos existentes.
     */
    @GetMapping
    public ResponseEntity<List<PrestamoDTO>> obtenerPrestamosActivos() {
        return ResponseEntity.ok(service.obtenerPrestamosActivos());
    }

    /**
     * Actualizar un préstamo existente.
     *
     * @param id                ID del préstamo.
     * @param prestamoUpdateDTO Datos actualizados del préstamo.
     * @return Respuesta HTTP con estado 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarPrestamo(@PathVariable String id,
                                                  @RequestBody PrestamoUpdateDTO clienteUpdateDTO) {
        service.actualizarPrestamo(id, clienteUpdateDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Eliminar un préstamo por ID.
     *
     * @param id ID del préstamo.
     * @return Respuesta HTTP con estado 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPrestamo(@PathVariable String id) {
        service.eliminarPrestamo(id);
        return ResponseEntity.ok().build();
    }


}
