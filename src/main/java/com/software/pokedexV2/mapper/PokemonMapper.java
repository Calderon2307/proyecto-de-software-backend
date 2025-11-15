package com.software.pokedexV2.mapper;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Pokemon;

public class PokemonMapper {

    //Este es el constructor privado
    private PokemonMapper() {}

    public static Pokemon toEntity(PokemonResponse response) {
        // aquí harás el mapeo con builder
        if (response == null) return null;

        return Pokemon.builder()
                //estos son setters del builder
                .idPokemon(response.getId())
                .nombre(response.getNombre())
                .tipoPrincipal(response.getTipoPrincipal())
                .tipoSecundario(response.getTipoSecundario())
                .spriteNormal(response.getSpriteNormal())
                .spriteShiny(response.getSpriteShiny())
                .build();
    }

}
