package com.software.pokedexV2.dto.request.Pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.entities.PokemonStat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonUpdateRequest {
    @NotNull(message = "El id del pokemon es obligatorio")
    @JsonProperty("id_pokemon")
    private Long id;

    @NotBlank(message = "El nombre del pokemon es obligatorio")
    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("tipos")
    private List<String> tipos;

    @JsonProperty(value = "stats")
    private List<PokemonStat> stats;

    @JsonProperty("sprite_normal")
    private String spriteNormal;

    @JsonProperty("sprite_shiny")
    private String spriteShiny;

}
