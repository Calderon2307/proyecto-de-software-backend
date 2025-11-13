package com.software.pokedexV2.dto.request.Entrenador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorLoginRequest {

    @JsonProperty(value = "usuario")
    @NotBlank(message = "El email no puede estar vacio.")
    @Email(message = "Debe ser un email valido.")
    private String email;

    @JsonProperty(value = "password")
    @NotBlank(message = "La contraseña no puede estar vacia.")
    @Size(min = 8, message = "La contraseña debe tener minimo 8 caracteres")
    private String password;
}
