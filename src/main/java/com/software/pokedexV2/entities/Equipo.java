package com.software.pokedexV2.entities;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "equipo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipo;

    @ManyToOne
    @JoinColumn(
            name = "id_entrenador",
            referencedColumnName = "id_entrenador",
            nullable = false
    )
    private Entrenador entrenador;

    @Column(name = "nombre_equipo", nullable = false)
    private String nombreEquipo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
//    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<PokemonEquipo> pokemonEquipo;
}
