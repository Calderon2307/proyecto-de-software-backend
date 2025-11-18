package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonUpdateRequest;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;

import java.util.List;

public interface PokemonService {

    // CREATE
    PokemonResponse createPokemon(PokemonRequest pokemonRequest);

    // READ
    PokemonResponse obtenerPorNombre(String nombre);
    List<PokemonResponse> getAll();
    List<PokemonResponse> getAllByTipoPrincipal(String tipoPrincipal);
    List<PokemonResponse> getAllByTipoSecundario(String tipoSecundario);

    // UPDATE
    PokemonResponse updatePokemon(PokemonUpdateRequest pokemonUpdateRequest);

    // DELETE
    PokemonResponse deletePokemonById(Long id);
    PokemonResponse deletePokemonByNombre(String nombre);

}
