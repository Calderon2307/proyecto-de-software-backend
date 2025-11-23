package com.software.pokedexV2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PokemonStat {
    @Column(name = "nombre_stat")
    private String statName;

    @Column(name = "valor_stat")
    private Integer baseStat;
}
