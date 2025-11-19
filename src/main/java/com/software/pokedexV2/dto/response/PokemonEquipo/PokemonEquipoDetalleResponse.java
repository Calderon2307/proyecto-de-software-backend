package com.software.pokedexV2.dto.response.PokemonEquipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonEquipoDetalleResponse {

    @JsonProperty("id_equipo")
    private Long idEquipo;

    @JsonProperty("nombre_equipo")
    private String nombreEquipo;

    @JsonProperty("pokemones")
    private List<PokemonEquipoResponse> pokemones;
}
