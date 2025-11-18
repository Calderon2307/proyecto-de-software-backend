package com.software.pokedexV2.service;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;

import java.util.List;

public interface EntrenadorService {
    //CREATE
    EntrenadorResponse createEntrenador(EntrenadorRequest entrenadorRequest);

    //READ
    EntrenadorResponse getById(Long id);
    EntrenadorResponse getByEmail(String email);
    List<EntrenadorResponse> getAll();
    List<EntrenadorResponse> getAllByRegionFav(String region);
    List<EntrenadorResponse> getAllByTipoFav(String tipo);
    List<EntrenadorResponse> getAllByPokemonFav(String pokemon);

    //UPDATE
    EntrenadorResponse updateEntrenador(EntrenadorUpdateRequest entrenadorUpdateRequest);

    //DELETE
    EntrenadorResponse deleteEntrenadorById(Long id);
    EntrenadorResponse deleteEntrenadorByEmail(String email);
}
