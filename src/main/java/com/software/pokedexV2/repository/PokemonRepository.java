package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//Qué entidad vas a manejar: Pokemon
//Tipo de dato es el id de la entidad: Long
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {

    boolean existsByNombre(String nombre);
    Optional<Pokemon> findByNombre(String nombre);
    List<Pokemon> findAllByTipoPrincipal(String tipoPrincipal);

    List<Pokemon> findAllByTipoSecundario(String tipoSecundario);

    void deleteByNombre(String nombre);
}
