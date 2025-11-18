package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoRequest;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoUpdateRequest;
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoResponse;
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoDetalleResponse;

import java.util.List;

public interface PokemonEquipoService {

    PokemonEquipoResponse addPokemonToTeam(PokemonEquipoRequest request);

    PokemonEquipoDetalleResponse getById(Long idEquipo);

    List<PokemonEquipoResponse> getByTeamId(Long idEquipo);

    PokemonEquipoResponse updatePokemonInTeam(PokemonEquipoUpdateRequest request);

    boolean removeFromTeam(Long idEquipo, String nombrePokemon, Integer posicion);
}
