package com.software.pokedexV2.mapper;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import java.util.List;

public class EquipoMapper {

    private EquipoMapper() {}


    public static Equipo toEntityCreate(EquipoRequest equipoRequest, Entrenador entrenador) {
        return Equipo.builder()
                .nombreEquipo(equipoRequest.getNombreEquipo())
                .entrenador(entrenador)
                .build();
    }


    public static void toEntityUpdate(Equipo equipo, EquipoRequest equipoRequest, Entrenador entrenador) {
        if (equipoRequest.getNombreEquipo() != null) {
            equipo.setNombreEquipo(equipoRequest.getNombreEquipo());
        }
    }


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
        return equipos.stream().map(EquipoMapper::toDTO).toList();
    }
}
