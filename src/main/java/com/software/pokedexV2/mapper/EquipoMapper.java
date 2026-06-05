package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorSummaryResponse;
import com.software.pokedexV2.dto.response.Equipo.EntrenadorEquiposResponse;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.dto.response.Equipo.EquipoSummaryResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonEquipoResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Entrenador;

import java.util.List;

public class EquipoMapper {

    private EquipoMapper() {}

    public static Equipo toEntity(EquipoResponse response) {
        if (response == null) return null;

        return Equipo.builder()
                .idEquipo(response.getIdEquipo())
                .nombreEquipo(response.getNombreEquipo())
                .build();
    }


    // CREATE
    public static Equipo toEntityCreate(EquipoRequest request, Entrenador entrenador) {
        return Equipo.builder()
                .nombreEquipo(request.getNombreEquipo())
                .entrenador(entrenador)
                .build();
    }

    // UPDATE
    public static void toEntityUpdate(Equipo equipo, EquipoUpdateRequest request) {

        if (request.getNombreEquipo() != null && !request.getNombreEquipo().isBlank()) {
            equipo.setNombreEquipo(request.getNombreEquipo());
        }
    }

    // DTO
    public static EquipoResponse toDTO(Equipo equipo, List<PokemonEquipoResponse> equipoPokemon) {
        return EquipoResponse.builder()
                .idEquipo(equipo.getIdEquipo())
                .nombreEquipo(equipo.getNombreEquipo())
                .entrenador(
                        EntrenadorSummaryResponse.builder()
                                .id(equipo.getEntrenador().getId())
                                .nombre(equipo.getEntrenador().getNombre())
                                .build()
                )
                .equipoPokemon(equipoPokemon)
                .fechaCreacion(equipo.getFechaCreacion())
                .build();
    }

    public static EquipoSummaryResponse toSummaryDTO(
            Equipo equipo,
            List<PokemonEquipoResponse> equipoPokemon
    ) {
        return EquipoSummaryResponse.builder()
                .idEquipo(equipo.getIdEquipo())
                .nombreEquipo(equipo.getNombreEquipo())
                .equipoPokemon(equipoPokemon)
                .fechaCreacion(equipo.getFechaCreacion())
                .build();
    }

    public static EntrenadorEquiposResponse toEntrenadorEquiposDTO(
            EntrenadorSummaryResponse entrenador,
            List<EquipoSummaryResponse> equiposPokemon
    ){
        return EntrenadorEquiposResponse.builder()
                .entrenador(entrenador)
                .equipos(equiposPokemon)
                .build();
    }
}
