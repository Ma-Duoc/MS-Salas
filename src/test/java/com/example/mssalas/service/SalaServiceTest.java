package com.example.mssalas.service;

import com.example.mssalas.dto.SalaDto;
import com.example.mssalas.exception.SalaException;
import com.example.mssalas.model.Sala;
import com.example.mssalas.repository.SalaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @Mock
    private SalaRepository salaRepository;

    @InjectMocks
    private SalaService salaService;

    private Sala sala;
    private SalaDto salaDto;

    @BeforeEach
    void setUp() {
        sala = new Sala();
        sala.setId(1L);
        sala.setNombre("Sala A");
        sala.setTipo("Conferencia");
        sala.setCapacidad(50);

        salaDto = new SalaDto();
        salaDto.setId(1L);
        salaDto.setNombre("Sala A");
        salaDto.setTipo("Conferencia");
        salaDto.setCapacidad(50);
    }

    @Test
    void crearSala_DebeGuardarCorrectamenteYRetornarSalaDtoConIdGenerado() {

        SalaDto salaDtoSinId = new SalaDto();
        salaDtoSinId.setNombre("Sala A");
        salaDtoSinId.setTipo("Conferencia");
        salaDtoSinId.setCapacidad(50);

        when(salaRepository.save(any(Sala.class))).thenReturn(sala);

        SalaDto resultado = salaService.crearSala(salaDtoSinId);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sala A", resultado.getNombre());
        assertEquals("Conferencia", resultado.getTipo());
        assertEquals(50, resultado.getCapacidad());

        verify(salaRepository, times(1)).save(any(Sala.class));
        verify(salaRepository, never()).findAll();
        verify(salaRepository, never()).findById(anyLong());
    }

    @Test
    void obtenerTodasLasSalas_DebeRetornarListaVaciaCuandoNoHayRegistros() {

        when(salaRepository.findAll()).thenReturn(List.of());

        List<SalaDto> resultado = salaService.obtenerTodasLasSalas();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());

        verify(salaRepository, times(1)).findAll();
        verify(salaRepository, never()).save(any(Sala.class));
        verify(salaRepository, never()).findById(anyLong());
    }

    @Test
    void obtenerTodasLasSalas_DebeRetornarListaConMultiplesSalasCuandoExistenRegistros() {

        Sala sala2 = new Sala();
        sala2.setId(2L);
        sala2.setNombre("Sala B");
        sala2.setTipo("Reunión");
        sala2.setCapacidad(20);

        when(salaRepository.findAll()).thenReturn(Arrays.asList(sala, sala2));

        List<SalaDto> resultado = salaService.obtenerTodasLasSalas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        assertEquals(1L, resultado.get(0).getId());
        assertEquals("Sala A", resultado.get(0).getNombre());

        assertEquals(2L, resultado.get(1).getId());
        assertEquals("Sala B", resultado.get(1).getNombre());

        verify(salaRepository, times(1)).findAll();
        verify(salaRepository, never()).save(any(Sala.class));
        verify(salaRepository, never()).findById(anyLong());
    }

    @Test
    void obtenerSalaPorId_DebeRetornarCorrectamenteCuandoElIdExiste() {

        Long id = 1L;

        when(salaRepository.findById(id))
                .thenReturn(Optional.of(sala));

        SalaDto resultado = salaService.obtenerSalaPorId(id);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Sala A", resultado.getNombre());
        assertEquals("Conferencia", resultado.getTipo());
        assertEquals(50, resultado.getCapacidad());

        verify(salaRepository, times(1)).findById(id);
        verify(salaRepository, never()).findAll();
        verify(salaRepository, never()).save(any(Sala.class));
    }

    @Test
    void obtenerSalaPorId_DebeLanzarSalaExceptionCuandoElIdNoExiste() {

        Long idNoExistente = 999L;

        when(salaRepository.findById(idNoExistente))
                .thenReturn(Optional.empty());

        SalaException exception = assertThrows(
                SalaException.class,
                () -> salaService.obtenerSalaPorId(idNoExistente));

        assertEquals(
                "Sala no encontrada con id: " + idNoExistente,
                exception.getMessage());

        verify(salaRepository, times(1)).findById(idNoExistente);
        verify(salaRepository, never()).findAll();
        verify(salaRepository, never()).save(any(Sala.class));
    }
}