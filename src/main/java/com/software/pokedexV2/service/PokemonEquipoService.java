package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoRequest;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoUpdateRequest;
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoResponse;

import java.util.List;

public interface PokemonEquipoService {

    // CREATE – agrega un Pokémon a un equipo
    PokemonEquipoResponse addPokemonToTeam(PokemonEquipoRequest request);

    // READ – obtener un registro específico de pokemon_equipo
    PokemonEquipoResponse getById(Long id);

    // READ – obtener todos los Pokémon de un equipo
    List<PokemonEquipoResponse> getByTeamId(Long idEquipo);

    // UPDATE – cambiar Pokémon o posición dentro del equipo
    PokemonEquipoResponse updatePokemonInTeam(PokemonEquipoUpdateRequest request);

    // DELETE – eliminar un Pokémon del equipo
    boolean removeFromTeam(Long id);
}
