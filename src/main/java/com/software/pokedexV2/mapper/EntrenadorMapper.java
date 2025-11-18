package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.entities.Pokemon;

import java.util.List;

public class EntrenadorMapper {

    private EntrenadorMapper() {}

    public static Entrenador toEntityCreate(
            EntrenadorRequest nuevoEntrenador,
            Pokemon pokemonFavorito
    ) {
       return Entrenador.builder()
               .nombre(nuevoEntrenador.getNombre())
               .email(nuevoEntrenador.getEmail())
               .contrasena(nuevoEntrenador.getContrasena())
               .regionPreferida(nuevoEntrenador.getRegionPreferida())
               .tipoPreferido(nuevoEntrenador.getTipoPreferido())
               .pokemonPreferido(pokemonFavorito)
               .build();
    }

    public static void toEntityUpdate(
            Entrenador entrenador,
            EntrenadorUpdateRequest updateReq,
            Pokemon pokemonFavorito
    ){
        if(pokemonFavorito != null) entrenador.setPokemonPreferido(pokemonFavorito);
        if(updateReq.getNombre() != null) entrenador.setNombre(updateReq.getNombre());
        if(updateReq.getRegionPreferida() != null) entrenador.setRegionPreferida(updateReq.getRegionPreferida());
        if(updateReq.getTipoPreferido() != null) entrenador.setTipoPreferido(updateReq.getTipoPreferido());
    }

    public static Entrenador responseToEntity(EntrenadorResponse resonse) {
        return Entrenador.builder()
                .id(resonse.getId())
                .nombre(resonse.getNombre())
                .email(resonse.getEmail())
                .build();
    }

    public static EntrenadorResponse toDTO(
        Entrenador entrenador
    ){
        return EntrenadorResponse.builder()
                .id(entrenador.getId())
                .nombre(entrenador.getNombre())
                .email(entrenador.getEmail())
                .tipoPreferido(entrenador.getTipoPreferido())
                .regionPreferida(entrenador.getRegionPreferida())
                .pokemonPreferido(
                        PokemonResponse
                                .builder()
                                .id(entrenador.getPokemonPreferido().getIdPokemon())
                                .nombre(entrenador.getPokemonPreferido().getNombre())
                                .tipoPrincipal(entrenador.getPokemonPreferido().getTipoPrincipal())
                                .tipoSecundario(entrenador.getPokemonPreferido().getTipoSecundario())
                                .spriteNormal(entrenador.getPokemonPreferido().getSpriteNormal())
                                .spriteShiny(entrenador.getPokemonPreferido().getSpriteShiny())
                                .build()
                )
                .build();
    }

    public static List<EntrenadorResponse> toDTOList(
        List<Entrenador> entrenadores
    ){
        return entrenadores.stream().map(EntrenadorMapper::toDTO).toList();
    }
}
