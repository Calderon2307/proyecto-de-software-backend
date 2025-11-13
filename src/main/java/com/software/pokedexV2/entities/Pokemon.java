package com.software.pokedexV2.entities;

import jakarta.persistence.*;
import lombok.*;


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
    @Column(name = "tipo_principal")
    private String tipoPrincipal;
    @Column(name = "tipo_secundario")
    private String tipoSecundario;
    @Column(name = "sprite_normal", columnDefinition = "TEXT")
    private String spriteNormal;
    @Column(name = "sprite_shiny", columnDefinition = "TEXT")
    private String spriteShiny;
}
