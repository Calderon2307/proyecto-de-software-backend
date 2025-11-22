package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonUpdateRequest;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.exception.Pokemon.PokemonAlredyExistsException;
import com.software.pokedexV2.exception.Pokemon.PokemonNotFoundException;
import com.software.pokedexV2.mapper.PokemonMapper;
import com.software.pokedexV2.repository.PokemonRepository;
import com.software.pokedexV2.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    // CREATE
    @Override
    @Transactional
    public PokemonResponse createPokemon(PokemonRequest pokemonRequest) {

        String nombreNormalizado = pokemonRequest.getNombre().toLowerCase().trim();
        List<String> tiposNormalizados = pokemonRequest.getTipos().stream()
                .map(t -> t.toLowerCase().trim())
                .toList();

        boolean existe = pokemonRepository.existsByNombre(nombreNormalizado);
        if (existe) {
            throw new PokemonAlredyExistsException("El pokemon ya existe");
        }

        PokemonRequest requestNormalizado = PokemonRequest.builder()
                .nombre(nombreNormalizado)
                .stats(pokemonRequest.getStats())
                .tipos(tiposNormalizados)
                .spriteNormal(pokemonRequest.getSpriteNormal())
                .spriteShiny(pokemonRequest.getSpriteShiny())
                .build();

        return PokemonMapper.toDTO(pokemonRepository.save(PokemonMapper.toEntityCreate(requestNormalizado)));
    }

    // READ
    @Override
    public PokemonResponse obtenerPorNombre(String nombre) {

        String nombreNormalizado = normalize(nombre);

        Pokemon pokemon = pokemonRepository.findByNombre(nombreNormalizado)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon no encontrado"));

        return PokemonMapper.toDTO(pokemon);
    }


    @Override
    public List<PokemonResponse> getAll() {
        List<Pokemon> pokemons = pokemonRepository.findAll();
        return PokemonMapper.toDTOList(pokemons);
    }

    // UPDATE
    @Override
    public PokemonResponse updatePokemon(PokemonUpdateRequest pokemonUpdateRequest) {
        String nombreNormalizado = normalize(pokemonUpdateRequest.getNombre());

        Pokemon pokemon = pokemonRepository.findByNombre(nombreNormalizado)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon no encontrado"));

        List<String> tiposNormalizados = null;

        if (pokemonUpdateRequest.getTipos() != null && !pokemonUpdateRequest.getTipos().isEmpty()) {
            tiposNormalizados = pokemonUpdateRequest.getTipos()
                    .stream()
                    .map(this::normalize) // usa el mismo método normalize()
                    .toList();
        }

        PokemonMapper.toEntityUpdate(pokemon, pokemonUpdateRequest, tiposNormalizados);

        return PokemonMapper.toDTO(pokemonRepository.save(pokemon));
    }

    // DELETE
    @Override
    public PokemonResponse deletePokemonById(Long id) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon no encontrado"));

        pokemonRepository.delete(pokemon);

        return PokemonMapper.toDTO(pokemon);
    }

    @Override
    public PokemonResponse deletePokemonByNombre(String nombre) {
        String nombreNormalizado = normalize(nombre);
        Pokemon pokemon = pokemonRepository.findByNombre(nombreNormalizado)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon no encontrado"));
        pokemonRepository.delete(pokemon);
        return PokemonMapper.toDTO(pokemon);
    }


    private String normalize(String value) {
        return value == null ? null : value.toLowerCase().trim();
    }

}
