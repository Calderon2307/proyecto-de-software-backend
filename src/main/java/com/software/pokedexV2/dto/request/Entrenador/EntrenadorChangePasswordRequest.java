package com.software.pokedexV2.dto.request.Entrenador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenadorChangePasswordRequest {
    @JsonProperty(value = "old_password")
    @NotBlank(message = "La contraseña antigua es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String oldPassword;

    @JsonProperty(value = "new_password")
    @NotBlank(message = "La contraseña nueva es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String newPassword;
}
