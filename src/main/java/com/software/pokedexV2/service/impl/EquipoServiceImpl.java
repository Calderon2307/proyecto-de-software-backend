package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.mapper.EquipoMapper;
import com.software.pokedexV2.repository.EquipoRepository;
import com.software.pokedexV2.service.EquipoService;
import com.software.pokedexV2.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final EntrenadorService entrenadorService;

    @Autowired
    public EquipoServiceImpl(EquipoRepository equipoRepository, EntrenadorService entrenadorService) {
        this.equipoRepository = equipoRepository;
        this.entrenadorService = entrenadorService;
    }

    // CREATE
    @Override
    @Transactional
    public EquipoResponse crearEquipo(EquipoRequest equipoRequest) {
        Entrenador entrenador = entrenadorService.obtenerEntrenador(equipoRequest.getIdEntrenador());
        Equipo equipo = EquipoMapper.toEntityCreate(equipoRequest, entrenador);
        equipoRepository.save(equipo);
        return EquipoMapper.toDTO(equipo);
    }

    // READ
    @Override
    public EquipoResponse obtenerEquipo(Long id) {
        Equipo equipo = equipoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Equipo no encontrado")
        );
        return EquipoMapper.toDTO(equipo);
    }

    @Override
    public List<EquipoResponse> obtenerEquiposPorEntrenador(Long idEntrenador) {
        List<Equipo> equipos = equipoRepository.findByEntrenador_Id(idEntrenador);
        return EquipoMapper.toDTOList(equipos);
    }

    // UPDATE
    @Override
    @Transactional
    public EquipoResponse actualizarEquipo(Long id, EquipoRequest equipoRequest) {
        Equipo equipo = equipoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Equipo no encontrado")
        );
        Entrenador entrenador = entrenadorService.obtenerEntrenador(equipoRequest.getIdEntrenador());
        EquipoMapper.toEntityUpdate(equipo, equipoRequest, entrenador);
        equipoRepository.save(equipo);
        return EquipoMapper.toDTO(equipo);
    }

    // DELETE
    @Override
    public boolean eliminarEquipo(Long id) {
        if (equipoRepository.existsById(id)) {
            equipoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
