package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.PokemonEquipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonEquipoRepository extends JpaRepository<PokemonEquipo, Long> {

    List<PokemonEquipo> findByEquipo_IdEquipoOrderByPosicion(Long equipoIdEquipo);

    // Buscar un registro concreto por equipo y posición
    Optional<PokemonEquipo> findByEquipo_IdEquipoAndPosicion(Long idEquipo, Integer posicion);

    void deleteByEquipo_IdEquipo(Long idEquipo);
    // Saber si un Pokémon ya está en el equipo 
   // boolean existsByEquipo_IdEquipoAndPokemon_IdPokemon(Long idEquipo, Long idPokemon);
}
