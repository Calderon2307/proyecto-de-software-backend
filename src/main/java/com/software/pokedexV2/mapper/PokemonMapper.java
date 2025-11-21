package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonUpdateRequest;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Pokemon;

import java.util.List;

public class PokemonMapper {

    private PokemonMapper() {}

    public static Pokemon toEntityCreate(PokemonRequest pokemonRequest) {
        return Pokemon.builder()
                .nombre(pokemonRequest.getNombre())
                .tipoPrincipal(pokemonRequest.getTipoPrincipal())
                .tipoSecundario(pokemonRequest.getTipoSecundario())
                .spriteNormal(pokemonRequest.getSpriteNormal())
                .spriteShiny(pokemonRequest.getSpriteShiny())
                .build();
    }

    public static void toEntityUpdate(
            Pokemon pokemon,
            PokemonUpdateRequest updateReq
    ) {
        if (updateReq.getTipoPrincipal() != null) {
            pokemon.setTipoPrincipal(updateReq.getTipoPrincipal());
        }
        if (updateReq.getTipoSecundario() != null) {
            pokemon.setTipoSecundario(updateReq.getTipoSecundario());
        }
        if (updateReq.getSpriteNormal() != null) {
            pokemon.setSpriteNormal(updateReq.getSpriteNormal());
        }
        if (updateReq.getSpriteShiny() != null) {
            pokemon.setSpriteShiny(updateReq.getSpriteShiny());
        }
    }

    public static PokemonResponse toDTO(Pokemon pokemon) {
        if (pokemon == null) {
            return null;
        }
        return PokemonResponse.builder()
                .id(pokemon.getIdPokemon())
                .nombre(pokemon.getNombre())
                .tipoPrincipal(pokemon.getTipoPrincipal())
                .tipoSecundario(pokemon.getTipoSecundario())
                .spriteNormal(pokemon.getSpriteNormal())
                .spriteShiny(pokemon.getSpriteShiny())
                .build();
    }

    /**
     * ✅ IMPORTANTE: Convertir PokemonResponse a Entity
     * Esto asegura que los datos del Pokemon estén correctamente mapeados
     */
    public static Pokemon toEntity(PokemonResponse pokemonResponse) {
        if (pokemonResponse == null) {
            return null;
        }
        return Pokemon.builder()
                .idPokemon(pokemonResponse.getId())
                .nombre(pokemonResponse.getNombre())
                .tipoPrincipal(pokemonResponse.getTipoPrincipal())
                .tipoSecundario(pokemonResponse.getTipoSecundario())
                .spriteNormal(pokemonResponse.getSpriteNormal())
                .spriteShiny(pokemonResponse.getSpriteShiny())
                .build();
    }

    public static List<PokemonResponse> toDTOList(List<Pokemon> pokemons) {
        if (pokemons == null) {
            return List.of();
        }
        return pokemons.stream()
                .map(PokemonMapper::toDTO)
                .toList();
    }

    public static List<Pokemon> toEntityList(List<PokemonResponse> pokemonResponses) {
        if (pokemonResponses == null) {
            return List.of();
        }
        return pokemonResponses.stream()
                .map(PokemonMapper::toEntity)
                .toList();
    }
}