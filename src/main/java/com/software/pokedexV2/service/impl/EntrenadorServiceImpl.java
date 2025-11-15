package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.exception.Entrenador.EntrenadorAlredyExistsException;
import com.software.pokedexV2.exception.Entrenador.EntrenadorNotFoundException;
import com.software.pokedexV2.mapper.EntrenadorMapper;
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
        boolean exist = entrenadorRepository.existsByEmail(entrenadorRequest.getEmail());
        if (exist) throw new EntrenadorAlredyExistsException("El email ya esta encontrado");

        PokemonResponse pokemon = pokemonService.obtenerPorNombre(entrenadorRequest.getNombrePokemonFavorito());

        String passwordEncrypted = passwordEncoder.encode(entrenadorRequest.getContrasena());

        EntrenadorRequest castEntrenador = EntrenadorRequest
                .builder()
                .nombre(entrenadorRequest.getNombre().toLowerCase().trim())
                .email(entrenadorRequest.getEmail().toLowerCase().trim())
                .contrasena(passwordEncrypted)
                .regionPreferida(entrenadorRequest.getRegionPreferida().toLowerCase().trim())
                .tipoPreferido(entrenadorRequest.getTipoPreferido().toLowerCase().trim())
                .build();

        return EntrenadorMapper.toDTO(
                entrenadorRepository.save(EntrenadorMapper.toEntityCreate(
                        castEntrenador,
                        PokemonMapper.entidadDesdeDTO(pokemon)
                ))
        );
    }

    //READ
    @Override
    public EntrenadorResponse getByEmail(String email) {
        return EntrenadorMapper.toDTO(
                entrenadorRepository.findByEmail(email).orElseThrow(
                        () -> new EntrenadorNotFoundException("Entrenador no encontrado")
                )
        );
    }

    @Override
    public List<EntrenadorResponse> getAll() {
        return EntrenadorMapper.toDTOList(
                entrenadorRepository.findAll()
        );
    }

    @Override
    public List<EntrenadorResponse> getAllByRegionFav(String region) {
        return EntrenadorMapper.toDTOList(
                entrenadorRepository.findAllByRegionPreferida(region)
        );
    }

    @Override
    public List<EntrenadorResponse> getAllByTipoFav(String tipo) {
        return EntrenadorMapper.toDTOList(
                entrenadorRepository.findAllByTipoPreferido(tipo)
        );
    }

    @Override
    public List<EntrenadorResponse> getAllByPokemonFav(String pokemon) {
        return EntrenadorMapper.toDTOList(
                entrenadorRepository.findAllByPokemonPreferido_Nombre(pokemon)
        );
    }

    //UPDATE
    @Override
    @Transactional
    public EntrenadorResponse updateEntrenador(EntrenadorUpdateRequest entrenadorUpdateRequest) {
        Entrenador entrenador = entrenadorRepository.findById(entrenadorUpdateRequest.getId()).orElseThrow(
                () -> new EntrenadorNotFoundException("Entrenador no encontrado")
        );

        EntrenadorUpdateRequest castEntrenador = EntrenadorUpdateRequest
                .builder()
                .id(entrenadorUpdateRequest.getId())
                .nombre(entrenadorUpdateRequest.getNombre().toLowerCase().trim())
                .regionPreferida(entrenadorUpdateRequest.getRegionPreferida().toLowerCase().trim())
                .tipoPreferido(entrenadorUpdateRequest.getTipoPreferido().toLowerCase().trim())
                .build();

        return null;
    }

    //DELETE
    @Override
    public EntrenadorResponse deleteEntrenadorById(Long id) {
        Entrenador entrenador = entrenadorRepository.findById(id).orElseThrow(
                () -> new EntrenadorNotFoundException("Entrenador no encontrado")
        );

        entrenadorRepository.deleteById(id);

        return EntrenadorMapper.toDTO(entrenador);
    }

    @Override
    public EntrenadorResponse deleteEntrenadorByEmail(String email) {
        Entrenador entrenador = entrenadorRepository.findByEmail(email).orElseThrow(
                () -> new EntrenadorNotFoundException("Entrenador no encontrado")
        );

        entrenadorRepository.deleteByEmail(email);

        return EntrenadorMapper.toDTO(entrenador);
    }
}
