package com.software.pokedexV2.dto.request.Entrenador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorUpdateRequest {

    @NotNull(message = "El ID del entrenador no puede estar vacio")
    private Long id;

    @JsonProperty(value = "pokemon_favorito")
    private String nombrePokemonFavorito;

    @JsonProperty(value = "nombre")
    private String nombre;

    @JsonProperty(value = "region_preferida")
    private String regionPreferida;

    @JsonProperty(value = "tipo_preferido")
    private String tipoPreferido;
}
