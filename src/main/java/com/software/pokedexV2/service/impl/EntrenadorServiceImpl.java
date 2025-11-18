package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.exception.Entrenador.EntrenadorAlredyExistsException;
import com.software.pokedexV2.exception.Entrenador.EntrenadorNotFoundException;
import com.software.pokedexV2.mapper.EntrenadorMapper;
import com.software.pokedexV2.mapper.PokemonMapper;
import com.software.pokedexV2.repository.EntrenadorRepository;
import com.software.pokedexV2.service.EntrenadorService;
import com.software.pokedexV2.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final PokemonService pokemonService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EntrenadorServiceImpl(
            EntrenadorRepository entrenadorRepository,
            PokemonService pokemonService,
            PasswordEncoder passwordEncoder
    ){
        this.entrenadorRepository = entrenadorRepository;
        this.pokemonService = pokemonService;
        this.passwordEncoder = passwordEncoder;
    }

    //CREATE
    @Override
    @Transactional
    public EntrenadorResponse createEntrenador(EntrenadorRequest entrenadorRequest) {
        boolean exist = entrenadorRepository.existsByEmail(entrenadorRequest.getEmail());
        if (exist) throw new EntrenadorAlredyExistsException("El email ya esta registrado");

        PokemonResponse pokemon = null;

        if(entrenadorRequest.getNombrePokemonFavorito() != null){
            pokemon = pokemonService.obtenerPorNombre(
                    entrenadorRequest.getNombrePokemonFavorito()
            );
        }

        String passwordEncrypted = passwordEncoder.encode(entrenadorRequest.getContrasena());

        EntrenadorRequest castEntrenador = EntrenadorRequest
                .builder()
                .nombre(entrenadorRequest.getNombre() != null ? entrenadorRequest.getNombre().toLowerCase().trim() : null)
                .email(entrenadorRequest.getEmail().toLowerCase().trim())
                .contrasena(passwordEncrypted)
                .regionPreferida(entrenadorRequest.getRegionPreferida() != null ? entrenadorRequest.getRegionPreferida().toLowerCase().trim() : null)
                .tipoPreferido(entrenadorRequest.getTipoPreferido() != null ? entrenadorRequest.getTipoPreferido().toLowerCase().trim() : null)
                .build();

        return EntrenadorMapper.toDTO(
                entrenadorRepository.save(EntrenadorMapper.toEntityCreate(
                        castEntrenador,
                        PokemonMapper.toEntity(pokemon)

                ))
        );
    }

    //READ
    @Override
    public EntrenadorResponse getById(Long id) {
        return EntrenadorMapper.toDTO(
                entrenadorRepository.findById(id).orElseThrow(
                        () -> new EntrenadorNotFoundException("Entrenador no encontrado")
                )
        );
    }

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
                .nombre(entrenadorUpdateRequest.getNombre() != null ? entrenadorUpdateRequest.getNombre().toLowerCase().trim() : null)
                .regionPreferida(entrenadorUpdateRequest.getRegionPreferida() != null ? entrenadorUpdateRequest.getRegionPreferida().toLowerCase().trim() : null)
                .tipoPreferido(entrenadorUpdateRequest.getTipoPreferido() != null ? entrenadorUpdateRequest.getTipoPreferido().toLowerCase().trim() : null)
                .nombrePokemonFavorito(entrenadorUpdateRequest.getNombrePokemonFavorito() != null ? entrenadorUpdateRequest.getNombrePokemonFavorito().toLowerCase().trim() : null)
                .build();

        Pokemon pokemon = entrenadorUpdateRequest.getNombrePokemonFavorito() != null ?
                PokemonMapper.toEntity(
                        pokemonService.obtenerPorNombre(entrenadorUpdateRequest.getNombrePokemonFavorito())
                )
                : null;

        EntrenadorMapper.toEntityUpdate(
                entrenador,
                castEntrenador,
                pokemon
        );

        return EntrenadorMapper.toDTO(
                entrenadorRepository.save(entrenador)
        );
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
    @Transactional
    public EntrenadorResponse deleteEntrenadorByEmail(String email) {
        Entrenador entrenador = entrenadorRepository.findByEmail(email).orElseThrow(
                () -> new EntrenadorNotFoundException("Entrenador no encontrado")
        );

        entrenadorRepository.deleteByEmail(email);

        return EntrenadorMapper.toDTO(entrenador);
    }
}
