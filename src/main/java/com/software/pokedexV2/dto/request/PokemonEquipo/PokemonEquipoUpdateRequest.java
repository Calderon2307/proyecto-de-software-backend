package com.software.pokedexV2.dto.request.PokemonEquipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonEquipoUpdateRequest {

    @NotNull(message = "El ID de pokemon_equipo es obligatorio")
    private Long id;

    @JsonProperty("posicion")
    private Integer posicion;

    @JsonProperty("id_pokemon")
    private Long idPokemon;
}
