package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoRequest;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoUpdateRequest;
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.entities.PokemonEquipo;
import com.software.pokedexV2.mapper.PokemonEquipoMapper;
import com.software.pokedexV2.repository.EquipoRepository;
import com.software.pokedexV2.repository.PokemonEquipoRepository;
import com.software.pokedexV2.repository.PokemonRepository;
import com.software.pokedexV2.service.PokemonEquipoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PokemonEquipoServiceImpl implements PokemonEquipoService {

    private final PokemonEquipoRepository pokemonEquipoRepository;
    private final EquipoRepository equipoRepository;
    private final PokemonRepository pokemonRepository;

    public PokemonEquipoServiceImpl(
            PokemonEquipoRepository pokemonEquipoRepository,
            EquipoRepository equipoRepository,
            PokemonRepository pokemonRepository
    ) {
        this.pokemonEquipoRepository = pokemonEquipoRepository;
        this.equipoRepository = equipoRepository;
        this.pokemonRepository = pokemonRepository;
    }

    // CREATE
    @Override
    public PokemonEquipoResponse addPokemonToTeam(PokemonEquipoRequest request) {

        // 1. Validar y obtener el equipo
        Equipo equipo = equipoRepository.findById(request.getIdEquipo())
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado con id: " + request.getIdEquipo()));

        // 2. Validar y obtener el Pokémon
        Pokemon pokemon = pokemonRepository.findById(request.getIdPokemon())
                .orElseThrow(() -> new RuntimeException("Pokémon no encontrado con id: " + request.getIdPokemon()));

        // 3. (Opcional) Validar que no exista ya un Pokémon en esa posición del equipo
        pokemonEquipoRepository.findByEquipo_IdEquipoAndPosicion(
                        equipo.getIdEquipo(),
                        request.getPosicion()
                )
                .ifPresent(existing -> {
                    throw new RuntimeException("Ya existe un Pokémon en la posición "
                            + request.getPosicion() + " para este equipo.");
                });

        // 4. Mapear request -> entidad y guardar
        PokemonEquipo entity = PokemonEquipoMapper.toEntityCreate(request, equipo, pokemon);
        PokemonEquipo saved = pokemonEquipoRepository.save(entity);

        // 5. Entidad -> DTO
        return PokemonEquipoMapper.toDTO(saved);
    }

    // READ by ID
    @Override
    @Transactional(readOnly = true)
    public PokemonEquipoResponse getById(Long id) {
        PokemonEquipo entity = pokemonEquipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro pokemon_equipo no encontrado con id: " + id));

        return PokemonEquipoMapper.toDTO(entity);
    }

    // READ – todos los Pokémon de un equipo
    @Override
    @Transactional(readOnly = true)
    public List<PokemonEquipoResponse> getByTeamId(Long idEquipo) {
        List<PokemonEquipo> lista = pokemonEquipoRepository.findByEquipo_IdEquipo(idEquipo);
        return PokemonEquipoMapper.toDTOList(lista);
    }

    // UPDATE
    @Override
    public PokemonEquipoResponse updatePokemonInTeam(PokemonEquipoUpdateRequest request) {

        // 1. Buscar la entidad a actualizar
        PokemonEquipo entity = pokemonEquipoRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Registro pokemon_equipo no encontrado con id: " + request.getId()));

        // 2. Si viene un nuevo id_pokemon, lo buscamos
        Pokemon nuevoPokemon = null;
        if (request.getIdPokemon() != null) {
            nuevoPokemon = pokemonRepository.findById(request.getIdPokemon())
                    .orElseThrow(() -> new RuntimeException("Pokémon no encontrado con id: " + request.getIdPokemon()));
        }

        // 3. Mapear campos del update request a la entidad
        PokemonEquipoMapper.toEntityUpdate(entity, request, nuevoPokemon);

        // 4. Guardar cambios
        PokemonEquipo updated = pokemonEquipoRepository.save(entity);

        // 5. Entidad -> DTO
        return PokemonEquipoMapper.toDTO(updated);
    }

    // DELETE
    @Override
    public boolean removeFromTeam(Long id) {

        if (!pokemonEquipoRepository.existsById(id)) {
            return false;
        }

        pokemonEquipoRepository.deleteById(id);
        return true;
    }
}
