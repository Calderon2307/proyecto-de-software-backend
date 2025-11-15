package com.software.pokedexV2.dto.request.Entrenador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorRequest {

    @JsonProperty(value = "pokemon_favorito")
    private String nombrePokemonFavorito;

    @JsonProperty(value = "nombre")
    @NotBlank(message = "El nombre no puede estar vacio.")
    private String nombre;

    @JsonProperty(value = "email")
    @Email(message = "El email debe ser uno valido.")
    @NotBlank(message = "El email no puede estar vacio.")
    private String email;

    @JsonProperty(value = "contrasenia")
    @NotBlank(message = "La conraseña no pude estar vacia.")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres o mas.")
    private String contrasena;

    @JsonProperty(value = "region_preferida")
    private String regionPreferida;

    @JsonProperty(value = "tipo_preferido")
    private String tipoPreferido;
}
