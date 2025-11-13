package com.software.pokedexV2.dto.request.Pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonRequest {

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

