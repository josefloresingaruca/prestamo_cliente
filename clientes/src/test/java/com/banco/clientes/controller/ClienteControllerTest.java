package com.banco.clientes.controller;

import com.banco.clientes.dto.ClienteCreateDTO;
import com.banco.clientes.dto.ClienteDTO;
import com.banco.clientes.dto.ClienteUpdateDTO;
import com.banco.clientes.model.TipoCliente;
import com.banco.clientes.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCrearCliente() throws Exception {
        ClienteCreateDTO createDTO = new ClienteCreateDTO("Juan", "juan@gmail.com", 30, TipoCliente.REGULAR);

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).crearCliente(any(ClienteCreateDTO.class));
    }

    @Test
    void testObtenerTodosClientes() throws Exception {
        ClienteDTO clienteDTO = new ClienteDTO("1", "Juan", "juan@gmail.com", 30, TipoCliente.REGULAR);
        when(clienteService.obtenerTodos()).thenReturn(List.of(clienteDTO));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"))
                .andExpect(jsonPath("$[0].email").value("juan@gmail.com"));

        verify(clienteService, times(1)).obtenerTodos();
    }

    @Test
    void testActualizarCliente() throws Exception {
        ClienteUpdateDTO updateDTO = new ClienteUpdateDTO("1", "Juan Actualizado", "juan@gmail.com", 31, TipoCliente.VIP);

        mockMvc.perform(put("/api/clientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).actualizarCliente(eq("1"), any(ClienteUpdateDTO.class));
    }

    @Test
    void testEliminarCliente() throws Exception {
        mockMvc.perform(delete("/api/clientes/1"))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).eliminarCliente("1");
    }

    @Test
    void testAccionPorTipo() throws Exception {
        when(clienteService.realizarAccionPorTipo("1")).thenReturn("Cliente VIP: Aplica un descuento en préstamos.");

        mockMvc.perform(get("/api/clientes/1/accion"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente VIP: Aplica un descuento en préstamos."));

        verify(clienteService, times(1)).realizarAccionPorTipo("1");
    }

}