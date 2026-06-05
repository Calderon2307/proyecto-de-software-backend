package com.software.pokedexV2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrenador")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "id_pokemon_favorito",
            referencedColumnName = "id_pokemon"
    )
    private Pokemon pokemonPreferido;

    @Column(
            name = "nombre_entrenador",
            nullable = false
    )
    private String nombre;

    @Column(
            name = "email",
            columnDefinition = "TEXT",
            nullable = false,
            unique = true
    )
    private String email;

    @Column(
            name = "contrasena",
            columnDefinition = "TEXT",
            nullable = false
    )
    private String contrasena;

    @Transient // Indica a JPA que este campo no es una columna de la tabla
    public String getPassword() {
        return this.contrasena;
    }
    @Column(name = "region_preferida")
    private String regionPreferida;

    @Column(name = "tipo_preferido")
    private String tipoPreferido;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void setFechaRegistro() { this.fechaRegistro = LocalDateTime.now(); }
}
