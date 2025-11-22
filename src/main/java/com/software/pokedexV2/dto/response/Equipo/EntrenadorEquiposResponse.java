package com.software.pokedexV2.dto.response.Equipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorSummaryResponse;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntrenadorEquiposResponse {
    @JsonProperty("entrenador")
    private EntrenadorSummaryResponse entrenador;

    @JsonProperty("equipos")
    private List<EquipoSummaryResponse> equipos;
}
