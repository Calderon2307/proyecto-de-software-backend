package com.software.pokedexV2.repository;

import com.software.pokedexV2.entities.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {

    //Obtener un equipo de un entrenador por su nombre
    Optional<Equipo> findByEntrenador_IdAndNombreEquipo(Long idEntrenador, String nombreEquipo);

    //Todos los equipos de 1 entrenador
    List<Equipo> findAllByEntrenador_Id(Long idEntrenador);

    //Comprobar que existe un equipo de un entrenador con X nombre
    boolean existsByNombreEquipoAndEntrenador_Id(String nombreEquipo, Long idEntrenador);

    //Comprobar el numero de equipos quetiene un entrenador
    int countAllByEntrenador_Id(Long idEntrenador);
}
