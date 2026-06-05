package com.software.pokedexV2.dto.response.Pokemon;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonResponse {
     @JsonProperty("id_pokemon")
     private Long id;
    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("tipos")
    private List<String> tipos;

    @JsonProperty("sprite_normal")
    private String spriteNormal;

    @JsonProperty("sprite_shiny")
    private String spriteShiny;
}
