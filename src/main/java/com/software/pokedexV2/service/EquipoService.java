package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;

import java.util.List;

public interface EquipoService {

    EquipoResponse crearEquipo(EquipoRequest equipoRequest);

    EquipoResponse obtenerEquipo(Long id);

    List<EquipoResponse> obtenerEquiposPorEntrenador(Long idEntrenador);

    EquipoResponse actualizarEquipo(Long id, EquipoRequest equipoRequest);

    boolean eliminarEquipo(Long id);
}
