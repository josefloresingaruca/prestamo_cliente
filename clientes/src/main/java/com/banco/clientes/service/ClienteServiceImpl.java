package com.banco.clientes.service;

import com.banco.clientes.dto.ClienteCreateDTO;
import com.banco.clientes.dto.ClienteDTO;
import com.banco.clientes.dto.ClienteUpdateDTO;
import com.banco.clientes.exception.ClienteNotFoundException;
import com.banco.clientes.mappers.ClienteMapper;
import com.banco.clientes.model.Cliente;
import com.banco.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementación del servicio para la gestión de clientes.
 */
@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper mapper = ClienteMapper.instancia;

    /**
     * Crea un nuevo cliente y lo guarda en el repositorio.
     *
     * @param clienteCreateDTO Datos del cliente a crear.
     */
    @Override
    public void crearCliente(ClienteCreateDTO clienteCreateDTO) {
        String idGenerado = UUID.randomUUID().toString();
        Cliente cliente = new Cliente(
                idGenerado,
                clienteCreateDTO.nombre(),
                clienteCreateDTO.email(),
                clienteCreateDTO.edad(),
                clienteCreateDTO.tipoCliente()
        );
        repository.save(cliente);
    }

    /**
     * Obtiene la lista de todos los clientes registrados.
     *
     * @return Lista de clientes en formato DTO.
     */
    @Override
    public List<ClienteDTO> obtenerTodos() {
        return mapper.listaClienteAListaClienteDTO(repository.findAll());
    }


    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param id               ID del cliente a actualizar.
     * @param clienteUpdateDTO Nuevos datos del cliente.
     * @throws ClienteNotFoundException Si el cliente no existe.
     */
    @Override
    public void actualizarCliente(String id, ClienteUpdateDTO clienteUpdateDTO  ) {
        Cliente clienteExistente = repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID " + id + " no encontrado") );

        Cliente clienteActualizado = new Cliente(
                clienteExistente.id(),
                clienteUpdateDTO.nombre(),
                clienteUpdateDTO.email(),
                clienteUpdateDTO.edad(),
                clienteUpdateDTO.tipoCliente()
        );
        repository.update(id, clienteActualizado);
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id ID del cliente a eliminar.
     * @throws ClienteNotFoundException Si el cliente no existe.
     */
    @Override
    public void eliminarCliente(String id) {
        repository.delete(id);
    }

    /**
     * Realiza una acción específica según el tipo de cliente.
     *
     * @param id ID del cliente.
     * @return Mensaje indicando la acción realizada.
     * @throws ClienteNotFoundException Si el cliente no existe.
     */
    @Override
    public String realizarAccionPorTipo(String id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID " + id + " no encontrado") );

        return switch (cliente.tipoCliente()) {
            case VIP -> "Cliente VIP: Aplica un descuento en préstamos.";
            case REGULAR -> "Cliente REGULAR: No aplica descuentos.";
            default -> "Cliente no clasificado.";
        };
    }
}
