package com.software.pokedexV2.dto.response.Pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.entities.PokemonStat;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonEquipoResponse {
    @JsonProperty("id_pokemon")
    private Long id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("sprite_normal")
    private String spriteNormal;

    @JsonProperty("sprite_shiny")
    private String spriteShiny;

    @JsonProperty(value = "stats")
    private List<PokemonStat> stats;

    @JsonProperty("posicion_equipo")
    private int posicionEquipo;
}
