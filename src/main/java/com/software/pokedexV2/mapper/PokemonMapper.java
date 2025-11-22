package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonUpdateRequest;
import com.software.pokedexV2.dto.response.Pokemon.PokemonEquipoResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Pokemon;

import java.util.List;

public class PokemonMapper {

    //Este es el constructor privado
    private PokemonMapper() {}

    public static Pokemon toEntityCreate(PokemonRequest request) {

        return Pokemon.builder()
                .nombre(request.getNombre())
                .tipos(request.getTipos())
                .stats(request.getStats())
                .spriteNormal(request.getSpriteNormal())
                .spriteShiny(request.getSpriteShiny())
                .build();

    }


    public static void toEntityUpdate(
            Pokemon pokemon,
            PokemonUpdateRequest updateReq,
            List<String> tipos
    ) {
        if (pokemon == null || updateReq == null) {
            return;
        }

        if (tipos != null && !tipos.isEmpty()) {
            pokemon.setTipos(updateReq.getTipos());
        }

        if (updateReq.getStats() != null && !updateReq.getStats().isEmpty()) {
            pokemon.setStats(updateReq.getStats());
        }

        if (updateReq.getSpriteNormal() != null) {
            pokemon.setSpriteNormal(updateReq.getSpriteNormal());
        }

        if (updateReq.getSpriteShiny() != null) {
            pokemon.setSpriteShiny(updateReq.getSpriteShiny());
        }
    }

    public static Pokemon toEntity(PokemonResponse response) {
        // aquí harás el mapeo con builder
        if (response == null) return null;

        return Pokemon.builder()
                //estos son setters del builder
                .idPokemon(response.getId())
                .nombre(response.getNombre())
                .tipos(response.getTipos())
                .spriteNormal(response.getSpriteNormal())
                .spriteShiny(response.getSpriteShiny())
                .build();
    }

    public static PokemonResponse toDTO(Pokemon pokemon) {
        if (pokemon == null) {
            return null;
        }

        return PokemonResponse.builder()
                .id(pokemon.getIdPokemon())
                .nombre(pokemon.getNombre())
                .tipos(pokemon.getTipos())
                .spriteNormal(pokemon.getSpriteNormal())
                .spriteShiny(pokemon.getSpriteShiny())
                .build();
    }

    public static PokemonEquipoResponse toEquipoResponseDTO(
            Pokemon pokemon,
            int posicionEquipo
    ) {
        return PokemonEquipoResponse.builder()
                .id(pokemon.getIdPokemon())
                .nombre(pokemon.getNombre())
                .spriteNormal(pokemon.getSpriteNormal())
                .spriteShiny(pokemon.getSpriteShiny())
                .stats(pokemon.getStats())
                .posicionEquipo(posicionEquipo)
                .build();
    }

    public static List<PokemonResponse> toDTOList(List<Pokemon> pokemons) {
        return pokemons.stream()
                .map(PokemonMapper::toDTO)
                .toList();
    }
}
