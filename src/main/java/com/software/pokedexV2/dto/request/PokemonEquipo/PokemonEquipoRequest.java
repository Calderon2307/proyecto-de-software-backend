package com.software.pokedexV2.dto.request.PokemonEquipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PokemonEquipoRequest {

    @JsonProperty("id_equipo")
    @NotNull(message = "El id del equipo es obligatorio.")
    private Long idEquipo;

    @JsonProperty("id_pokemon")
    @NotNull(message = "El id del pokemon es obligatorio.")
    private Long idPokemon;

    @JsonProperty("posicion")
    @NotNull(message = "La posición es obligatoria.")
    @Min(value = 1, message = "La posición debe ser mayor o igual a 1.")
    private Integer posicion;
}
