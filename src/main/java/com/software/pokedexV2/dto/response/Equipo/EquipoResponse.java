package com.software.pokedexV2.dto.response.Equipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorSummaryResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonEquipoResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoResponse {

    @JsonProperty("id_equipo")
    private Long idEquipo;

    @JsonProperty("entrenador")
    private EntrenadorSummaryResponse entrenador;

    @JsonProperty("nombre_equipo")
    private String nombreEquipo;

    @JsonProperty("equipo_pokemon")
    private List<PokemonEquipoResponse> equipoPokemon;

    @JsonProperty("fecha_creacion")
    private LocalDateTime fechaCreacion;
}
