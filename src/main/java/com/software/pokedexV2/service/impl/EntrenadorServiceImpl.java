package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.exception.Entrenador.EntrenadorAlredyExistsException;
import com.software.pokedexV2.exception.Entrenador.EntrenadorNotFoundException;
import com.software.pokedexV2.mapper.EntrenadorMapper;
import com.software.pokedexV2.mapper.PokemonMapper;
import com.software.pokedexV2.repository.EntrenadorRepository;
import com.software.pokedexV2.repository.PokemonRepository;
import com.software.pokedexV2.service.EntrenadorService;
import com.software.pokedexV2.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class EntrenadorServiceImpl implements EntrenadorService {

    private static final Logger log = LoggerFactory.getLogger(EntrenadorServiceImpl.class);

    private final EntrenadorRepository entrenadorRepository;
    private final PokemonService pokemonService;
    private final PasswordEncoder passwordEncoder;
    private final PokemonRepository pokemonRepository;

    @Autowired
    public EntrenadorServiceImpl(
            EntrenadorRepository entrenadorRepository,
            PokemonService pokemonService,
            PasswordEncoder passwordEncoder,
            PokemonRepository pokemonRepository){
        this.entrenadorRepository = entrenadorRepository;
        this.pokemonService = pokemonService;
        this.passwordEncoder = passwordEncoder;
        this.pokemonRepository = pokemonRepository;
    }

    //CREATE
    @Override
    @Transactional
    public EntrenadorResponse createEntrenador(EntrenadorRequest entrenadorRequest) {

        log.info("INICIO REGISTRO: Procesando email={}", entrenadorRequest.getEmail());

        if (entrenadorRepository.existsByEmail(entrenadorRequest.getEmail())) {
            throw new EntrenadorAlredyExistsException("El email ya esta registrado");
        }

        Pokemon pokemonEntity = null;

        // 1. Procesar Pokémon favorito si viene en el request
        PokemonRequest pokemonFavRequest = entrenadorRequest.getPokemonFavorito();

        if (pokemonFavRequest != null &&
                pokemonFavRequest.getNombre() != null &&
                !pokemonFavRequest.getNombre().isBlank()) {

            String nombreNormalizado = pokemonFavRequest.getNombre()
                    .toLowerCase()
                    .trim();

            log.info("PUNTO 1: Pokémon favorito recibido: {}", nombreNormalizado);

            try {
                // INTENTO 1: Buscar en BD
                PokemonResponse pokemonBD = pokemonService.obtenerPorNombre(nombreNormalizado);
                pokemonEntity = PokemonMapper.toEntity(pokemonBD);

                log.info("PUNTO 2A: Pokémon EXISTE en BD. ID={}", pokemonEntity.getIdPokemon());

            } catch (RuntimeException e) {
                log.warn("PUNTO 2A: El Pokémon no existía en BD. Se intentará crear: {}", nombreNormalizado);

                try {
                    // Ajustar el request original con el nombre normalizado
                    pokemonFavRequest.setNombre(nombreNormalizado);

                    PokemonResponse creado = pokemonService.createPokemon(pokemonFavRequest);
                    pokemonEntity = PokemonMapper.toEntity(creado);

                    log.info("PUNTO 2B: Pokémon CREADO correctamente. ID={}", pokemonEntity.getIdPokemon());

                } catch (RuntimeException errCreacion) {
                    log.error("ERROR: No se pudo crear el Pokémon {}", nombreNormalizado, errCreacion);
                    throw new EntrenadorNotFoundException("El Pokémon favorito no existe y no pudo ser creado.");
                }
            }
        } else {
            log.warn("No se envió Pokémon favorito en el request.");
        }

        // 2. Sanitizar info del entrenador
        EntrenadorRequest castEntrenador = EntrenadorRequest.builder()
                .nombre(entrenadorRequest.getNombre().toLowerCase().trim())
                .email(entrenadorRequest.getEmail().toLowerCase().trim())
                .contrasena(passwordEncoder.encode(entrenadorRequest.getContrasena()))
                .regionPreferida(entrenadorRequest.getRegionPreferida() != null
                        ? entrenadorRequest.getRegionPreferida().toLowerCase().trim()
                        : null)
                .tipoPreferido(entrenadorRequest.getTipoPreferido() != null
                        ? entrenadorRequest.getTipoPreferido().toLowerCase().trim()
                        : null)
                .pokemonFavorito(null) // se ignora aquí, se maneja abajo
                .build();

        // 3. Crear entidad
        Entrenador entrenadorEntity = EntrenadorMapper.toEntityCreate(castEntrenador, pokemonEntity);
        Entrenador saved = entrenadorRepository.save(entrenadorEntity);

        if (pokemonEntity != null) {
            saved = entrenadorRepository.saveAndFlush(saved);
            log.info("Relación Pokémon persistida. ID={}", saved.getPokemonPreferido().getIdPokemon());
        }

        // 4. Mapear respuesta
        EntrenadorResponse response = EntrenadorMapper.toDTO(saved);
        log.info("Entrenador creado con Pokémon favorito: {}",
                response.getPokemonPreferido() != null ? response.getPokemonPreferido().getNombre() : "NULL");

        return response;
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

        Pokemon pokemon = null;

        if (entrenadorUpdateRequest.getPokemonFavorito() != null &&
                entrenadorUpdateRequest.getPokemonFavorito().getNombre() != null &&
                !entrenadorUpdateRequest.getPokemonFavorito().getNombre().isBlank()) {

            String nombreNormalizado = entrenadorUpdateRequest
                    .getPokemonFavorito()
                    .getNombre()
                    .toLowerCase()
                    .trim();

            // ----- 1. Revisar si existe en BD -----
            boolean exists = pokemonRepository.existsByNombre(nombreNormalizado);

            if (exists) {
                // Si existe, obtenerlo y convertirlo a entidad
                PokemonResponse pokemonBD = pokemonService.obtenerPorNombre(nombreNormalizado);
                pokemon = PokemonMapper.toEntity(pokemonBD);

            } else {
                // ----- 2. Si NO existe → crearlo -----
                entrenadorUpdateRequest.getPokemonFavorito().setNombre(nombreNormalizado);

                PokemonResponse creado = pokemonService.createPokemon(
                        entrenadorUpdateRequest.getPokemonFavorito()
                );

                pokemon = PokemonMapper.toEntity(creado);
            }
        }

        EntrenadorMapper.toEntityUpdate(
                entrenador,
                entrenadorUpdateRequest,
                pokemon
        );

        return EntrenadorMapper.toDTO(
                entrenadorRepository.saveAndFlush(entrenador)
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