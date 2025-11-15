package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoRequest;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoUpdateRequest;
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.PokemonEquipo;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.entities.Equipo;

import java.util.List;

public class PokemonEquipoMapper {

    private PokemonEquipoMapper() {
    }

    public static PokemonEquipo toEntityCreate(
            PokemonEquipoRequest request,
            Equipo equipo,
            Pokemon pokemon) {
        return PokemonEquipo.builder()
                .equipo(equipo)
                .pokemon(pokemon)
                .posicion(request.getPosicion())
                .build();
    }

    public static void toEntityUpdate(
            PokemonEquipo pokemonEquipo,
            PokemonEquipoUpdateRequest updateReq,
            Pokemon pokemonNuevo) {

        if (updateReq.getPosicion() != null) {
            pokemonEquipo.setPosicion(updateReq.getPosicion());
        }

        if (pokemonNuevo != null) {
            pokemonEquipo.setPokemon(pokemonNuevo);
        }
    }

    public static PokemonEquipoResponse toDTO(PokemonEquipo pe) {

        return PokemonEquipoResponse.builder()
                .id(pe.getIdPokemonEquipo())
                .idEquipo(pe.getEquipo().getIdEquipo())
                .posicion(pe.getPosicion())
                .pokemon(
                        PokemonResponse.builder()
                                .id(pe.getPokemon().getIdPokemon())
                                .nombre(pe.getPokemon().getNombre())
                                .tipoPrincipal(pe.getPokemon().getTipoPrincipal())
                                .tipoSecundario(pe.getPokemon().getTipoSecundario())
                                .spriteNormal(pe.getPokemon().getSpriteNormal())
                                .spriteShiny(pe.getPokemon().getSpriteShiny())
                                .build())
                .build();
    }

    public static List<PokemonEquipoResponse> toDTOList(List<PokemonEquipo> lista) {
        return lista.stream().map(PokemonEquipoMapper::toDTO).toList();
    }
}
