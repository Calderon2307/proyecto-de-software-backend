package com.software.pokedexV2.dto.request.Pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonRequest {
    @NotBlank(message = "El nombre del pokemon es obligatorio")
    @JsonProperty("nombre")
    private String nombre;
    @NotBlank(message = "El tipo principal del pokemon es obligatorio")
    @JsonProperty("tipo_principal")
    private String tipoPrincipal;
    @JsonProperty("tipo_secundario")
    private String tipoSecundario;
    @JsonProperty("sprite_normal")
    private String spriteNormal;
    @JsonProperty("sprite_shiny")
    private String spriteShiny;
}

