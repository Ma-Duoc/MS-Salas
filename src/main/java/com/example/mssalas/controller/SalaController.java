package com.example.mssalas.controller;

import com.example.mssalas.dto.SalaDto;
import com.example.mssalas.service.SalaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
@RequiredArgsConstructor
public class SalaController {

    private final SalaService salaService;

    @PostMapping
    public ResponseEntity<SalaDto> crearSala(@Valid @RequestBody SalaDto salaDto) {
        SalaDto nuevaSala = salaService.crearSala(salaDto);
        return new ResponseEntity<>(nuevaSala, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SalaDto>> listarSalas() {
        List<SalaDto> salas = salaService.obtenerTodasLasSalas();
        return ResponseEntity.ok(salas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaDto> obtenerSalaPorId(@PathVariable Long id) {
        SalaDto sala = salaService.obtenerSalaPorId(id);
        return ResponseEntity.ok(sala);
    }

}
