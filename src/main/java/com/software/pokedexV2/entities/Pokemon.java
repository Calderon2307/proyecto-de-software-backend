package com.software.pokedexV2.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pokemon")
    private Long idPokemon;

    @Column(name = "nombre")
    private String nombre;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pokemon_tipos", joinColumns = @JoinColumn(name = "id_pokemon"))
    private List<String> tipos;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pokemon_stats", joinColumns = @JoinColumn(name = "id_pokemon"))
    private List<PokemonStat> stats;

    @Column(name = "sprite_normal", columnDefinition = "TEXT")
    private String spriteNormal;

    @Column(name = "sprite_shiny", columnDefinition = "TEXT")
    private String spriteShiny;
}
