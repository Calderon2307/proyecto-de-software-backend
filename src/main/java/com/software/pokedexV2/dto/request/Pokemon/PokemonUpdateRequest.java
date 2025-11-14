package com.software.pokedexV2.dto.request.Pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonUpdateRequest {
    @NotNull(message = "El id del pokemon es obligatorio")
    @JsonProperty("id_pokemon")
    private Long id;
    @JsonProperty("tipo_principal")
    private String tipoPrincipal;

    @JsonProperty("tipo_secundario")
    private String tipoSecundario;

    @JsonProperty("sprite_normal")
    private String spriteNormal;

    @JsonProperty("sprite_shiny")
    private String spriteShiny;

}
