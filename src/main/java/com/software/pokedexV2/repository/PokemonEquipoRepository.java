package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.PokemonEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonEquipoRepository extends JpaRepository<PokemonEquipo, Long> {

    // Obtener todos los Pokémon de un equipo por el id del equipo
    List<PokemonEquipo> findByEquipo_IdEquipo(Long idEquipo);

    // Buscar un registro concreto por equipo y posición
    Optional<PokemonEquipo> findByEquipo_IdEquipoAndPosicion(Long idEquipo, Integer posicion);

    // Saber si un Pokémon ya está en el equipo 
   // boolean existsByEquipo_IdEquipoAndPokemon_IdPokemon(Long idEquipo, Long idPokemon);
}
