package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.response.Equipo.EntrenadorEquiposResponse;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;

import java.util.List;

public interface EquipoService {

    // CREATE
    EquipoResponse createTeam(EquipoRequest request);

    // READ
    EquipoResponse getTeamById(Long id); // Keep for testing
    EntrenadorEquiposResponse getTeamsByTrainerId(Long trainerId);
    List<EquipoResponse> getAllTeams();


    // UPDATE
    EquipoResponse updateTeam(Long idEquipo, EquipoUpdateRequest request);

    // DELETE
    EquipoResponse deleteTeam(Long id);
}
