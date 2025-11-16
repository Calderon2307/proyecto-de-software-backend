package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    List<Equipo> findByEntrenador_Id(Long idEntrenador);

    boolean existsByNombreEquipoAndEntrenador_Id(String nombreEquipo, Long idEntrenador);

    Optional<Equipo> findByEntrenador_IdAndNombreEquipo(Long idEntrenador, String nombreEquipo);
}
