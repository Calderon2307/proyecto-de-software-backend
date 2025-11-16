package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;

import java.util.List;

public interface EquipoService {

    // CREATE
    EquipoResponse createTeam(EquipoRequest request);

    // READ
    EquipoResponse getTeamById(Long id); // Keep for testing
    List<EquipoResponse> getTeamsByTrainerId(Long trainerId);
    EquipoResponse getTeamByTrainerIdAndName(Long trainerId, String teamName);


    // UPDATE
    EquipoResponse updateTeam(EquipoUpdateRequest request);

    // DELETE
    boolean deleteTeam(Long id);
}
