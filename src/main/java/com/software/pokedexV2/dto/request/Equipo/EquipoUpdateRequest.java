package com.software.pokedexV2.dto.request.Equipo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipoUpdateRequest {

    @JsonProperty("nombre_equipo")
    private String nombreEquipo;

    @JsonProperty("equipo_pokemon")
    private List<PokemonRequest> equipoPokemon;
}