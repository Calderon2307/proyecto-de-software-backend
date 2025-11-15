package com.software.pokedexV2.dto.response.Equipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoResponse {

    @JsonProperty("id_equipo")
    private Long idEquipo;

    @JsonProperty("nombre_equipo")
    private String nombreEquipo;

    @JsonProperty("entrenador")
    private EntrenadorResponse entrenador;

    @JsonProperty("fecha_creacion")
    private LocalDateTime fechaCreacion;
}
