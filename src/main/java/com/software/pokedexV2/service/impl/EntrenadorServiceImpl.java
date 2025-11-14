package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.repository.EntrenadorRepository;
import com.software.pokedexV2.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    //private final PokemonService pokemonService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EntrenadorServiceImpl(
            EntrenadorRepository entrenadorRepository,
            //PokemonService pokemonService,
            PasswordEncoder passwordEncoder
    ){
        this.entrenadorRepository = entrenadorRepository;
        //this.pokemonService = pokemonService;
        this.passwordEncoder = passwordEncoder;
    }

    //CREATE
    @Override
    @Transactional
    public EntrenadorResponse createEntrenador(EntrenadorRequest entrenadorRequest) {
        return null;
    }

    //READ
    @Override
    public EntrenadorResponse getByEmail(String email) {
        return null;
    }

    @Override
    public List<EntrenadorResponse> getAll() {
        return List.of();
    }

    @Override
    public List<EntrenadorResponse> getAllByRegionFav(String region) {
        return List.of();
    }

    @Override
    public List<EntrenadorResponse> getAllByTipoFav(String tipo) {
        return List.of();
    }

    @Override
    public List<EntrenadorResponse> getAllByPokemonFav(String pokemon) {
        return List.of();
    }

    //UPDATE
    @Override
    @Transactional
    public EntrenadorResponse updateEntrenador(EntrenadorUpdateRequest entrenadorUpdateRequest) {
        return null;
    }

    //DELETE
    @Override
    public EntrenadorResponse deleteEntrenadorById(String id) {
        return null;
    }

    @Override
    public EntrenadorResponse deleteEntrenadorByEmail(String email) {
        return null;
    }
}
