package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Entrenador;

import java.util.List;

public class EquipoMapper {

    private EquipoMapper() {}

    // CREATE
    public static Equipo toEntityCreate(EquipoRequest request, Entrenador entrenador) {
        return Equipo.builder()
                .nombreEquipo(request.getNombreEquipo())
                .entrenador(entrenador)
                .build();
    }

    // UPDATE
    public static void toEntityUpdate(Equipo equipo, EquipoUpdateRequest request, Entrenador entrenador) {

        if (request.getNombreEquipo() != null) {
            equipo.setNombreEquipo(request.getNombreEquipo());
        }
    }

    // DTO
    public static EquipoResponse toDTO(Equipo equipo) {
        return EquipoResponse.builder()
                .idEquipo(equipo.getIdEquipo())
                .nombreEquipo(equipo.getNombreEquipo())

                .entrenador(
                        EntrenadorResponse.builder()
                                .id(equipo.getEntrenador().getId())
                                .nombre(equipo.getEntrenador().getNombre())
                                .build()
                )

                .fechaCreacion(equipo.getFechaCreacion())
                .build();
    }

    public static List<EquipoResponse> toDTOList(List<Equipo> equipos) {
        return equipos.stream()
                .map(EquipoMapper::toDTO)
                .toList();
    }
}
