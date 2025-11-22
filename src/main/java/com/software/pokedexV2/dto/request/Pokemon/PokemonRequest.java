package com.software.pokedexV2.dto.request.Pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.entities.PokemonStat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonRequest {
    @NotBlank(message = "El nombre del pokemon es obligatorio")
    @JsonProperty("nombre")
    private String nombre;

    @NotEmpty(message = "Los tipos son obligatorios")
    @JsonProperty("tipos")
    private List<String> tipos;

    @NotEmpty(message = "Las estadisticas son obligatorias")
    @JsonProperty(value = "stats")
    private List<PokemonStat> stats;

    @JsonProperty("sprite_normal")
    private String spriteNormal;

    @JsonProperty("sprite_shiny")
    private String spriteShiny;
}

