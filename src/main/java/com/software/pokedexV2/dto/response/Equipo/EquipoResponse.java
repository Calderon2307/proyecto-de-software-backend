package com.software.pokedexV2.dto.response.Equipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoResponse {

    @JsonProperty("id_equipo")
    private Long idEquipo;

    @JsonProperty("nombre_equipo")
    private String nombreEquipo;

    @JsonProperty("id_entrenador")
    private Long idEntrenador;

}
