package com.example.mssalas.Repository;

import com.example.mssalas.model.Sala;
import com.example.mssalas.repository.SalaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SalaRepositoryTest {

    @Autowired
    private SalaRepository salaRepository;

    @Test
    void guardarSala_DebePersistirDatos() {

        Sala sala = new Sala();
        sala.setNombre("Sala Test");
        sala.setTipo("Laboratorio");
        sala.setCapacidad(30);

        Sala guardada = salaRepository.save(sala);

        assertNotNull(guardada.getId());
        assertEquals("Sala Test", guardada.getNombre());
        assertEquals("Laboratorio", guardada.getTipo());
        assertEquals(30, guardada.getCapacidad());
    }

    @Test
    void buscarPorId_DebeEncontrarSala() {

        Sala sala = new Sala();
        sala.setNombre("Sala A");
        sala.setTipo("Conferencia");
        sala.setCapacidad(50);

        Sala guardada = salaRepository.save(sala);

        Optional<Sala> encontrada = salaRepository.findById(guardada.getId());

        assertTrue(encontrada.isPresent());
        assertEquals("Sala A", encontrada.get().getNombre());
    }

    @Test
    void findAll_DebeRetornarTodasLasSalas() {

        Sala sala1 = new Sala();
        sala1.setNombre("Sala 1");
        sala1.setTipo("Aula");
        sala1.setCapacidad(20);

        Sala sala2 = new Sala();
        sala2.setNombre("Sala 2");
        sala2.setTipo("Laboratorio");
        sala2.setCapacidad(40);

        salaRepository.save(sala1);
        salaRepository.save(sala2);

        List<Sala> salas = salaRepository.findAll();

        assertEquals(2, salas.size());
    }
}
