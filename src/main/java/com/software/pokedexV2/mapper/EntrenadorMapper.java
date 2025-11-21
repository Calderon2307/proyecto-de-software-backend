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
        Pokemon pokemonPreferido = entrenador.getPokemonPreferido();

        PokemonResponse pokemonResponse = null;

        if (pokemonPreferido != null) {
            // La corrección para el problema de 'null' en la respuesta:
            // Delegar la construcción del DTO a PokemonMapper
            pokemonResponse = PokemonMapper.toDTO(pokemonPreferido);
        }

        return EntrenadorResponse.builder()
                .id(entrenador.getId())
                .nombre(entrenador.getNombre())
                .email(entrenador.getEmail())
                .tipoPreferido(entrenador.getTipoPreferido())
                .regionPreferida(entrenador.getRegionPreferida())
                .pokemonPreferido(pokemonResponse) // Pasa el objeto (que ahora debe estar completo)
                .build();
    }

    public static List<EntrenadorResponse> toDTOList(
            List<Entrenador> entrenadores
    ){
        return entrenadores.stream().map(EntrenadorMapper::toDTO).toList();
    }
}