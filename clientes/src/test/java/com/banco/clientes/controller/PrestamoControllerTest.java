package com.banco.clientes.controller;

import com.banco.clientes.dto.PrestamoCreateDTO;
import com.banco.clientes.dto.PrestamoDTO;
import com.banco.clientes.dto.PrestamoUpdateDTO;
import com.banco.clientes.model.EstadoPrestamo;
import com.banco.clientes.service.PrestamoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PrestamoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PrestamoService prestamoService;

    @InjectMocks
    private PrestamoController prestamoController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(prestamoController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    @Test
    void testCrearPrestamo() throws Exception {
        PrestamoCreateDTO createDTO = new PrestamoCreateDTO(1000, "cliente1", LocalDate.now(), EstadoPrestamo.PENDIENTE);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk());

        verify(prestamoService, times(1)).crearPrestamo(any(PrestamoCreateDTO.class));
    }

    @Test
    void testObtenerPrestamosActivos() throws Exception {
        PrestamoDTO prestamoDTO = new PrestamoDTO("1", 1000, "cliente1", LocalDate.now(), EstadoPrestamo.PENDIENTE, 1050);
        when(prestamoService.obtenerPrestamosActivos()).thenReturn(List.of(prestamoDTO));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].monto").value(1000))
                .andExpect(jsonPath("$[0].estado").value("PENDIENTE"));

        verify(prestamoService, times(1)).obtenerPrestamosActivos();
    }

    @Test
    void testActualizarPrestamo() throws Exception {
        PrestamoUpdateDTO updateDTO = new PrestamoUpdateDTO("1", 1200, "cliente1", LocalDate.now(), EstadoPrestamo.PAGADO);

        mockMvc.perform(put("/api/prestamos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk());

        verify(prestamoService, times(1)).actualizarPrestamo(eq("1"), any(PrestamoUpdateDTO.class));
    }

    @Test
    void testEliminarPrestamo() throws Exception {
        mockMvc.perform(delete("/api/prestamos/1"))
                .andExpect(status().isOk());

        verify(prestamoService, times(1)).eliminarPrestamo("1");
    }
}