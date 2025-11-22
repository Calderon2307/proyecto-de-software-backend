package com.software.pokedexV2.dto.request.Equipo;
import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoRequest {

    @JsonProperty("id_entrenador")
    @NotNull(message = "El ID del entrenador no puede ser nulo.")
    private Long idEntrenador;

    @JsonProperty("nombre_equipo")
    @NotBlank(message = "El nombre del equipo no puede estar vacío.")
    private String nombreEquipo;

    @JsonProperty("equipo_pokemon")
    private List<PokemonRequest> equipoPokemon;
}
