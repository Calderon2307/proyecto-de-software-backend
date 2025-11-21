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

        log.info("INICIO REGISTRO: Procesando email={}", entrenadorRequest.getEmail());

        boolean exist = entrenadorRepository.existsByEmail(entrenadorRequest.getEmail());
        if (exist) throw new EntrenadorAlredyExistsException("El email ya esta registrado");

        String nombrePokemon = entrenadorRequest.getNombrePokemonFavorito();
        Pokemon pokemonEntity = null;

        // 1. Validar y buscar el Pokémon solo si se proporciona el nombre.
        if (StringUtils.hasText(nombrePokemon)) {
            try {
                // Normalizar la cadena antes de la búsqueda
                String nombreNormalizado = nombrePokemon.toLowerCase().trim();
                log.info("PUNTO 1: Buscando Pokémon normalizado: {}", nombreNormalizado);

                PokemonResponse pokemonResponse = pokemonService.obtenerPorNombre(
                        nombreNormalizado
                );

                pokemonEntity = PokemonMapper.toEntity(pokemonResponse);
                log.info("PUNTO 2: Pokémon encontrado. ID de la Entidad: {}", pokemonEntity.getIdPokemon());
            } catch (RuntimeException e) {
                log.error("ERROR CRÍTICO (Punto 2): Pokémon no encontrado para: {}", nombrePokemon, e);
                throw new EntrenadorNotFoundException("El Pokémon favorito especificado no fue encontrado.");
            }
        } else {
            log.warn("PUNTO 1: Nombre de Pokémon vacío o nulo.");
        }


        String passwordEncrypted = passwordEncoder.encode(entrenadorRequest.getContrasena());

        // 2. Construir el DTO sanitizado para la entidad
        EntrenadorRequest castEntrenador = EntrenadorRequest
                .builder()
                .nombre(entrenadorRequest.getNombre() != null ? entrenadorRequest.getNombre().toLowerCase().trim() : null)
                .email(entrenadorRequest.getEmail().toLowerCase().trim())
                .contrasena(passwordEncrypted)
                .regionPreferida(entrenadorRequest.getRegionPreferida() != null ? entrenadorRequest.getRegionPreferida().toLowerCase().trim() : null)
                .tipoPreferido(entrenadorRequest.getTipoPreferido() != null ? entrenadorRequest.getTipoPreferido().toLowerCase().trim() : null)
                .build();

        // 3. Guardar la entidad (el pokemonEntity ya está asignado en el mapper)
        Entrenador newEntrenador = EntrenadorMapper.toEntityCreate(castEntrenador, pokemonEntity);
        Entrenador savedEntrenador = entrenadorRepository.save(newEntrenador);

        // ✅ SOLUCIÓN DEFINITIVA: Usar saveAndFlush() para persistir inmediatamente
        // y luego recargar la entidad para asegurar que la relación esté en la sesión
        if (pokemonEntity != null) {
            savedEntrenador = entrenadorRepository.saveAndFlush(savedEntrenador);
            log.info("PUNTO 3: Relación Pokémon persistida. ID: {}", savedEntrenador.getPokemonPreferido().getIdPokemon());
        } else {
            log.warn("PUNTO 3: No se asignó Pokémon. El campo se devolverá NULL.");
        }

        // 4. Mapear a DTO y retornar
        EntrenadorResponse response = EntrenadorMapper.toDTO(savedEntrenador);
        log.info("PUNTO 4: Respuesta final. Pokémon Preferido (DTO): {}",
                response.getPokemonPreferido() != null ? response.getPokemonPreferido().getNombre() : "NULL");

        return response;
    }

    //READ
    @Override
    public EntrenadorResponse getById(Long id) {
        return EntrenadorMapper.toDTO(
                entrenadorRepository.findByIdWithPokemon(id).orElseThrow(
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

        if (entrenadorUpdateRequest.getNombrePokemonFavorito() != null && !entrenadorUpdateRequest.getNombrePokemonFavorito().isBlank()) {
            pokemon = PokemonMapper.toEntity(
                    pokemonService.obtenerPorNombre(
                            entrenadorUpdateRequest.getNombrePokemonFavorito().toLowerCase().trim()
                    )
            );
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