package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
    Optional<Entrenador> findByEmail(String email);
    List<Entrenador> findAllByRegionPreferida(String regionPreferida);
    List<Entrenador> findAllByTipoPreferido(String tipoPreferido);
    List<Entrenador> findAllByPokemonPreferido_Nombre(String nombre);
    boolean existsByEmail(String email);
    int countAllByRegionPreferida(String regionPreferida);
    int countAllByTipoPreferido(String tipoPreferido);
    int countAllByPokemonPreferido_Nombre(String nombre);

    @Query("SELECT e FROM Entrenador e " +
                    "WHERE e.pokemonPreferido.tipoPrincipal = :tipo " +
                    "OR " +
                    "e.pokemonPreferido.tipoSecundario = :tipo")
    List<Entrenador> findByPokemonPreferidoType(String tipo);

    void deleteByEmail(String email);
}
