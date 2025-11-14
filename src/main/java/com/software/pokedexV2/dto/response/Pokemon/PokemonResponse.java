package com.software.pokedexV2.dto.response.Pokemon;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonResponse {
     @JsonProperty("id_pokemon")
     private Long id;
    @JsonProperty("nombre")
    private String nombre;
    @JsonProperty("tipo_principal")
    private String tipoPrincipal;
    @JsonProperty("tipo_secundario")
    private String tipoSecundario;
    @JsonProperty("sprite_normal")
    private String spriteNormal;
    @JsonProperty("sprite_shiny")
    private String spriteShiny;
}
