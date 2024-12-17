package com.banco.clientes.service;

import com.banco.clientes.dto.PrestamoCreateDTO;
import com.banco.clientes.dto.PrestamoUpdateDTO;
import com.banco.clientes.exception.ClienteNotFoundException;
import com.banco.clientes.exception.PrestamoNotFoundException;
import com.banco.clientes.model.Cliente;
import com.banco.clientes.model.EstadoPrestamo;
import com.banco.clientes.model.Prestamo;
import com.banco.clientes.model.TipoCliente;
import com.banco.clientes.repository.ClienteRepository;
import com.banco.clientes.repository.PrestamoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PrestamoServiceImplTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    private Prestamo prestamo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        prestamo = new Prestamo("1", 1000, "cliente1", LocalDate.now(), EstadoPrestamo.PENDIENTE, 1050);
    }

    @Test
    void testCrearPrestamo() {
        PrestamoCreateDTO createDTO = new PrestamoCreateDTO(1000, "cliente1", LocalDate.now(), EstadoPrestamo.PENDIENTE);
        when(clienteRepository.findById("cliente1"))
                .thenReturn(Optional.of(new Cliente("cliente1", "Juan", "juan@gmail.com", 30, TipoCliente.REGULAR)));

        assertDoesNotThrow(() -> prestamoService.crearPrestamo(createDTO));
        verify(prestamoRepository, times(1)).save(any(Prestamo.class));
    }

    @Test
    void testCrearPrestamoClienteNotFoundException() {
        PrestamoCreateDTO createDTO = new PrestamoCreateDTO(1000, "cliente1", LocalDate.now(), EstadoPrestamo.PENDIENTE);
        when(clienteRepository.findById("cliente1")).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> prestamoService.crearPrestamo(createDTO));
    }

    @Test
    void testActualizarPrestamo() {
        PrestamoUpdateDTO updateDTO = new PrestamoUpdateDTO("1", 1200, "cliente1", LocalDate.now(), EstadoPrestamo.PAGADO);
        when(prestamoRepository.findById("1")).thenReturn(Optional.of(prestamo));
        when(clienteRepository.findById("cliente1"))
                .thenReturn(Optional.of(new Cliente("cliente1", "Juan", "juan@gmail.com", 30, TipoCliente.VIP)));

        assertDoesNotThrow(() -> prestamoService.actualizarPrestamo("1", updateDTO));
        verify(prestamoRepository, times(1)).update(eq("1"), any(Prestamo.class));
    }

    @Test
    void testEliminarPrestamo() {
        doNothing().when(prestamoRepository).delete("1");
        assertDoesNotThrow(() -> prestamoService.eliminarPrestamo("1"));
        verify(prestamoRepository, times(1)).delete("1");
    }

    @Test
    void testEliminarPrestamoNotFoundException() {
        doThrow(new PrestamoNotFoundException("Prestamo no encontrado")).when(prestamoRepository).delete("1");
        assertThrows(PrestamoNotFoundException.class, () -> prestamoService.eliminarPrestamo("1"));
    }
}