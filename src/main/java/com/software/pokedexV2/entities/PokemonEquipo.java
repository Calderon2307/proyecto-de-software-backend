package com.software.pokedexV2.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pokemon_equipo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pokemon_equipo")
    private Long idPokemonEquipo;

    // Muchos PokemonEquipo pertenecen a un mismo Equipo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_equipo",
            referencedColumnName = "id_equipo",
            nullable = false
    )
    private Equipo equipo;

    // Muchos PokemonEquipo pueden apuntar al mismo Pokemon
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_pokemon",
            referencedColumnName = "id_pokemon",
            nullable = false
    )
    private Pokemon pokemon;

    @Column(name = "posicion")
    private Integer posicion;
}
