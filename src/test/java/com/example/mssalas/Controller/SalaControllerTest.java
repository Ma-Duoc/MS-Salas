package com.example.mssalas.Controller;

import com.example.mssalas.controller.SalaController;
import com.example.mssalas.dto.SalaDto;
import com.example.mssalas.service.SalaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaController.class)
class SalaControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private SalaService salaService;

        @Test
        void crearSala_DebeRetornar201() throws Exception {

                SalaDto entrada = new SalaDto();
                entrada.setNombre("Sala A");
                entrada.setTipo("Conferencia");
                entrada.setCapacidad(50);

                SalaDto salida = new SalaDto();
                salida.setId(1L);
                salida.setNombre("Sala A");
                salida.setTipo("Conferencia");
                salida.setCapacidad(50);

                when(salaService.crearSala(org.mockito.ArgumentMatchers.any()))
                                .thenReturn(salida);

                mockMvc.perform(post("/api/salas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(entrada)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.nombre").value("Sala A"))
                                .andExpect(jsonPath("$.tipo").value("Conferencia"))
                                .andExpect(jsonPath("$.capacidad").value(50));
        }

        @Test
        void listarSalas_DebeRetornar200YLista() throws Exception {

                SalaDto sala = new SalaDto();
                sala.setId(1L);
                sala.setNombre("Sala A");
                sala.setTipo("Conferencia");
                sala.setCapacidad(50);

                when(salaService.obtenerTodasLasSalas())
                                .thenReturn(List.of(sala));

                mockMvc.perform(get("/api/salas"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(1))
                                .andExpect(jsonPath("$[0].nombre").value("Sala A"));
        }

        @Test
        void obtenerSalaPorId_DebeRetornar200() throws Exception {

                SalaDto sala = new SalaDto();
                sala.setId(1L);
                sala.setNombre("Sala A");
                sala.setTipo("Conferencia");
                sala.setCapacidad(50);

                when(salaService.obtenerSalaPorId(1L))
                                .thenReturn(sala);

                mockMvc.perform(get("/api/salas/1"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.nombre").value("Sala A"))
                                .andExpect(jsonPath("$.tipo").value("Conferencia"))
                                .andExpect(jsonPath("$.capacidad").value(50));
        }
}
