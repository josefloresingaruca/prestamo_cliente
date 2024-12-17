package com.banco.clientes.controller;

import com.banco.clientes.dto.ClienteCreateDTO;
import com.banco.clientes.dto.ClienteDTO;
import com.banco.clientes.dto.ClienteUpdateDTO;
import com.banco.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para la gestión de clientes.
 */
@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    /**
     * Crear un nuevo cliente.
     *
     * @param clienteCreateDTO Datos del cliente a crear.
     * @return Respuesta HTTP con estado 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Void> crearCliente(@RequestBody ClienteCreateDTO clienteCreateDTO) {
        service.crearCliente(clienteCreateDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Obtener todos los clientes registrados.
     *
     * @return Lista de clientes existentes.
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    /**
     * Actualizar un cliente existente.
     *
     * @param id               ID del cliente.
     * @param clienteUpdateDTO Datos actualizados del cliente.
     * @return Respuesta HTTP con estado 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarCliente(@PathVariable String id,
                                                  @RequestBody ClienteUpdateDTO clienteUpdateDTO) {
        service.actualizarCliente(id, clienteUpdateDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Eliminar un cliente por ID.
     *
     * @param id ID del cliente.
     * @return Respuesta HTTP con estado 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable String id) {
        service.eliminarCliente(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Realizar acción específica para un cliente.
     *
     * @param id ID del cliente.
     * @return Resultado de la acción realizada.
     */
    @GetMapping("/{id}/accion")
    public ResponseEntity<String> accionPorTipo(@PathVariable String id) {
        return ResponseEntity.ok(service.realizarAccionPorTipo(id));
    }
}
