package com.software.pokedexV2.dto.response.Entrenador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorResponse {

    private Long id;

    @JsonProperty(value = "nombre_entrenador")
    private String nombre;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "pokemon_favorito")
    private PokemonResponse pokemonPreferido;

    @JsonProperty(value = "region_preferida")
    private String regionPreferida;

    @JsonProperty(value = "tipo_preferido")
    private String tipoPreferido;
}
