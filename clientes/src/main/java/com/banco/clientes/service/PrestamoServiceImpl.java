package com.banco.clientes.service;

import com.banco.clientes.dto.PrestamoCreateDTO;
import com.banco.clientes.dto.PrestamoDTO;
import com.banco.clientes.dto.PrestamoUpdateDTO;
import com.banco.clientes.exception.ClienteNotFoundException;
import com.banco.clientes.exception.PrestamoNotFoundException;
import com.banco.clientes.mappers.PrestamoMapper;
import com.banco.clientes.model.Cliente;
import com.banco.clientes.model.EstadoPrestamo;
import com.banco.clientes.model.Prestamo;
import com.banco.clientes.model.TipoCliente;
import com.banco.clientes.repository.ClienteRepository;
import com.banco.clientes.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementación del servicio para la gestión de préstamos.
 */
@Service
@RequiredArgsConstructor
public class PrestamoServiceImpl  implements PrestamoService{

    private final PrestamoRepository repository;
    private final ClienteRepository clienteRepository;
    private final PrestamoMapper mapper = PrestamoMapper.instancia;

    /**
     * Crea un nuevo préstamo y lo guarda en el repositorio.
     *
     * @param prestamoCreateDTO Datos del préstamo a crear.
     * @throws ClienteNotFoundException Si el cliente asociado no existe.
     */
    @Override
    public void crearPrestamo(PrestamoCreateDTO prestamoCreateDTO) {
        String idGenerado = UUID.randomUUID().toString();
        Prestamo prestamo = new Prestamo(
                idGenerado,
                prestamoCreateDTO.monto(),
                prestamoCreateDTO.clienteId(),
                prestamoCreateDTO.fecha(),
                prestamoCreateDTO.estado(),
                calcularMontoTotal(prestamoCreateDTO.clienteId(), prestamoCreateDTO.monto())
        );
        repository.save(prestamo);
    }



    /**
     * Obtiene la lista de préstamos activos (estado PENDIENTE).
     *
     * @return Lista de préstamos activos en formato DTO.
     */
    @Override
    public List<PrestamoDTO> obtenerPrestamosActivos() {
        return repository.findAll().stream()
                .filter(prestamo -> prestamo.estado() == EstadoPrestamo.PENDIENTE)
                .map(mapper::prestamoAPrestamoDTO)
                .toList();
    }

    /**
     * Actualiza los datos de un préstamo existente.
     *
     * @param id                ID del préstamo a actualizar.
     * @param prestamoUpdateDTO Nuevos datos del préstamo.
     * @throws PrestamoNotFoundException Si el préstamo no existe.
     * @throws ClienteNotFoundException  Si el cliente asociado no existe.
     */
    @Override
    public void actualizarPrestamo(String id, PrestamoUpdateDTO prestamoUpdateDTO) {
        Prestamo prestamoExistente = repository.findById(id)
                .orElseThrow(() -> new PrestamoNotFoundException("Prestamo con ID " + id + " no encontrado") );

        Prestamo prestamoActualizado = new Prestamo(
                prestamoExistente.id(),
                prestamoUpdateDTO.monto(),
                prestamoUpdateDTO.clienteId(),
                prestamoUpdateDTO.fecha(),
                prestamoUpdateDTO.estado(),
                calcularMontoTotal(prestamoUpdateDTO.clienteId(), prestamoUpdateDTO.monto())
        );
        repository.update(id, prestamoActualizado);

    }

    /**
     * Elimina un préstamo por su ID.
     *
     * @param id ID del préstamo a eliminar.
     * @throws PrestamoNotFoundException Si el préstamo no existe.
     */
    @Override
    public void eliminarPrestamo(String id) {
        repository.delete(id);
    }


    /**
     * Calcula el monto total del préstamo basado en el tipo de cliente.
     *
     * @param clienteId ID del cliente.
     * @param monto     Monto base del préstamo.
     * @return Monto total calculado.
     * @throws ClienteNotFoundException Si el cliente no existe.
     */
    public double calcularMontoTotal(String clienteId, double monto) {
        Cliente cliente= clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con ID " + clienteId + " no encontrado"));

        double tasa = cliente.tipoCliente() == TipoCliente.VIP ? 0.05 : 0.10;
        return monto + (monto * tasa);
    }
}
