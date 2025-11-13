package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Qué entidad vas a manejar: Pokemon
//Tipo de dato es el id de la entidad: Long
//todo: AGREGAR @REPOSITORY
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

}
