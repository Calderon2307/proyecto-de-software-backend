package com.software.pokedexV2.dto.response.Entrenador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorSummaryResponse {
    private Long id;

    @JsonProperty(value = "nombre_entrenador")
    private String nombre;
}
