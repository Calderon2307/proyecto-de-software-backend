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

    @JsonProperty("nombre_pokemon")
    @NotNull(message = "El nombre del pokemon es obligatorio.")
    private String nombrePokemon;

    @JsonProperty("posicion")
    @NotNull(message = "La posición es obligatoria.")
    @Min(value = 1, message = "La posición debe ser mayor o igual a 1.")
    private Integer posicion;
}
