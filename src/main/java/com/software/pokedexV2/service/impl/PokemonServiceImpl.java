package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonUpdateRequest;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.mapper.PokemonMapper;
import com.software.pokedexV2.repository.PokemonRepository;
import com.software.pokedexV2.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    // CREATE
    @Override
    public PokemonResponse createPokemon(PokemonRequest pokemonRequest) {
        Pokemon pokemon = PokemonMapper.toEntityCreate(pokemonRequest);
        Pokemon guardado = pokemonRepository.save(pokemon);
        return PokemonMapper.toDTO(guardado);
    }

    // READ
    @Override
    public PokemonResponse obtenerPorNombre(String nombre) {
        Pokemon pokemon = pokemonRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Pokemon no encontrado"));
        return PokemonMapper.toDTO(pokemon);
    }

    @Override
    public List<PokemonResponse> getAll() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        return PokemonMapper.toDTOList(pokemons);
    }

    @Override
    public List<PokemonResponse> getAllByTipoPrincipal(String tipoPrincipal) {
        List<Pokemon> pokemons = pokemonRepository.findAllByTipoPrincipal(tipoPrincipal);
        return PokemonMapper.toDTOList(pokemons);
    }

    @Override
    public List<PokemonResponse> getAllByTipoSecundario(String tipoSecundario) {
        List<Pokemon> pokemons = pokemonRepository.findAllByTipoSecundario(tipoSecundario);
        return PokemonMapper.toDTOList(pokemons);
    }

    // UPDATE
    @Override
    public PokemonResponse updatePokemon(PokemonUpdateRequest pokemonUpdateRequest) {
        Pokemon pokemon = pokemonRepository.findById(pokemonUpdateRequest.getId())
                .orElseThrow(() -> new RuntimeException("Pokemon no encontrado"));

        PokemonMapper.toEntityUpdate(pokemon, pokemonUpdateRequest);

        Pokemon actualizado = pokemonRepository.save(pokemon);
        return PokemonMapper.toDTO(actualizado);
    }

    // DELETE
    @Override
    public PokemonResponse deletePokemonById(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pokemon no encontrado"));

        pokemonRepository.delete(pokemon);

        return PokemonMapper.toDTO(pokemon);
    }

    @Override
    public PokemonResponse deletePokemonByNombre(String nombre) {
        Pokemon pokemon = pokemonRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Pokemon no encontrado"));

        pokemonRepository.deleteByNombre(nombre);

        return PokemonMapper.toDTO(pokemon);
    }
}
