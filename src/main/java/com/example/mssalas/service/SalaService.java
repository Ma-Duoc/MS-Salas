package com.example.mssalas.service;

import com.example.mssalas.dto.SalaDto;
import com.example.mssalas.exception.SalaException;
import com.example.mssalas.model.Sala;
import com.example.mssalas.repository.SalaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaDto crearSala(SalaDto salaDto) {

        Sala sala = convertirDtoAEntidad(salaDto);

        Sala salaGuardada =
                salaRepository.save(sala);

        return convertirEntidadADto(salaGuardada);
    }

    public List<SalaDto> obtenerTodasLasSalas() {

        return salaRepository.findAll()
                .stream()
                .map(this::convertirEntidadADto)
                .collect(Collectors.toList());
    }

    public SalaDto obtenerSalaPorId(Long id) {

        Sala sala = salaRepository.findById(id)
                .orElseThrow(() ->
                        new SalaException(
                                "Sala no encontrada con id: " + id
                        )
                );

        return convertirEntidadADto(sala);
    }

    private Sala convertirDtoAEntidad(SalaDto salaDto) {

        Sala sala = new Sala();

        sala.setNombre(salaDto.getNombre());
        sala.setTipo(salaDto.getTipo());
        sala.setCapacidad(salaDto.getCapacidad());

        return sala;
    }

    private SalaDto convertirEntidadADto(Sala sala) {

        SalaDto salaDto = new SalaDto();

        salaDto.setId(sala.getId());
        salaDto.setNombre(sala.getNombre());
        salaDto.setTipo(sala.getTipo());
        salaDto.setCapacidad(sala.getCapacidad());

        return salaDto;
    }
}
