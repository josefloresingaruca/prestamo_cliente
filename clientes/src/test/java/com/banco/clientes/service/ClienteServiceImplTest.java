package com.banco.clientes.service;


import com.banco.clientes.dto.ClienteCreateDTO;
import com.banco.clientes.dto.ClienteUpdateDTO;
import com.banco.clientes.exception.ClienteNotFoundException;
import com.banco.clientes.model.Cliente;
import com.banco.clientes.model.TipoCliente;
import com.banco.clientes.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    private Cliente cliente;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente("1", "Juan", "juan@gmail.com", 30, TipoCliente.REGULAR);
    }

    @Test
    void testCrearCliente() {
        ClienteCreateDTO createDTO = new ClienteCreateDTO("Juan", "juan@gmail.com", 30, TipoCliente.REGULAR);
        clienteService.crearCliente(createDTO);
        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void testActualizarCliente() {

        ClienteUpdateDTO updateDTO = new ClienteUpdateDTO("1", "Juan Actualizado", "juan@gmail.com", 31, TipoCliente.VIP);
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));
        assertDoesNotThrow(() -> clienteService.actualizarCliente("1", updateDTO));
        verify(clienteRepository, times(1)).update(eq("1"), any(Cliente.class));
    }

    @Test
    void testActualizarClienteNotFoundException() {
        ClienteUpdateDTO updateDTO = new ClienteUpdateDTO("1", "Juan Actualizado", "juan@gmail.com", 31, TipoCliente.VIP);
        when(clienteRepository.findById("1")).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class, () -> clienteService.actualizarCliente("1", updateDTO));
    }
    @Test
    void testEliminarCliente() {
        when(clienteRepository.findById("1")).thenReturn(Optional.of(cliente));
        assertDoesNotThrow(() -> clienteService.eliminarCliente("1"));
        verify(clienteRepository, times(1)).delete("1");
    }


}