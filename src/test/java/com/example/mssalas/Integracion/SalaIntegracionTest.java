package com.example.mssalas.Integracion;

import com.example.mssalas.dto.SalaDto;
import com.example.mssalas.model.Sala;
import com.example.mssalas.repository.SalaRepository;
import com.example.mssalas.service.SalaService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class SalaIntegracionTest {

    @Autowired
    private SalaService salaService;

    @Autowired
    private SalaRepository salaRepository;

    @Test
    void crearSala_DebePersistirEnBaseDatos() {

        // Arrange
        SalaDto dto = new SalaDto();
        dto.setNombre("Sala Integracion");
        dto.setTipo("Laboratorio");
        dto.setCapacidad(30);

        // Act
        SalaDto resultado = salaService.crearSala(dto);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.getId());

        Optional<Sala> salaGuardada = salaRepository.findById(resultado.getId());

        assertTrue(salaGuardada.isPresent());
        assertEquals("Sala Integracion", salaGuardada.get().getNombre());
        assertEquals("Laboratorio", salaGuardada.get().getTipo());
        assertEquals(30, salaGuardada.get().getCapacidad());
    }

    @Test
    void obtenerSalaPorId_DebeRetornarSalaPersistida() {

        // Arrange
        Sala sala = new Sala();
        sala.setNombre("Sala Persistida");
        sala.setTipo("Auditorio");
        sala.setCapacidad(100);

        Sala salaGuardada = salaRepository.save(sala);

        // Act
        SalaDto resultado = salaService.obtenerSalaPorId(salaGuardada.getId());

        // Assert
        assertNotNull(resultado);
        assertEquals(salaGuardada.getId(), resultado.getId());
        assertEquals("Sala Persistida", resultado.getNombre());
        assertEquals("Auditorio", resultado.getTipo());
        assertEquals(100, resultado.getCapacidad());
    }
}