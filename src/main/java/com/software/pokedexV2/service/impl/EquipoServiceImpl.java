package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
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
    public EquipoResponse createTeam(EquipoRequest request) {

        Entrenador trainer = entrenadorService.getById(request.getIdEntrenador());

        boolean exists = equipoRepository.existsByNombreEquipoAndEntrenador_Id(
                request.getNombreEquipo(),
                request.getIdEntrenador()
        );

        if (exists) {
            throw new RuntimeException("El entrenador ya tiene un equipo con ese nombre.");
        }

        Equipo team = EquipoMapper.toEntityCreate(request, trainer);
        equipoRepository.save(team);

        return EquipoMapper.toDTO(team);
    }

    // READ
    @Override
    public EquipoResponse getTeamById(Long id) {
        Equipo team = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado."));
        return EquipoMapper.toDTO(team);
    }

    @Override
    public List<EquipoResponse> getTeamsByTrainerId(Long trainerId) {
        return EquipoMapper.toDTOList(
                equipoRepository.findByEntrenador_Id(trainerId)
        );
    }

    @Override
    public EquipoResponse getTeamByTrainerIdAndName(Long trainerId, String name) {
        Equipo team = equipoRepository
                .findByEntrenador_IdAndNombreEquipo(trainerId, name)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado."));
        return EquipoMapper.toDTO(team);
    }

    // UPDATE
    @Override
    @Transactional
    public EquipoResponse updateTeam(EquipoUpdateRequest request) {

        Equipo team = equipoRepository.findById(request.getIdEquipo())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        EquipoMapper.toEntityUpdate(team, request, team.getEntrenador());

        equipoRepository.save(team);
        return EquipoMapper.toDTO(team);
    }

    // DELETE
    @Override
    public boolean deleteTeam(Long id) {
        if (!equipoRepository.existsById(id)) return false;
        equipoRepository.deleteById(id);
        return true;
    }
}
