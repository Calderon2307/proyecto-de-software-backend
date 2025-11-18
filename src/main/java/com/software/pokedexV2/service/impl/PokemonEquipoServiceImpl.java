package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoRequest;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoUpdateRequest;
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoResponse;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.entities.PokemonEquipo;
import com.software.pokedexV2.mapper.EquipoMapper;
import com.software.pokedexV2.mapper.PokemonMapper;
import com.software.pokedexV2.mapper.PokemonEquipoMapper;
import com.software.pokedexV2.repository.PokemonEquipoRepository;
import com.software.pokedexV2.service.EquipoService;
import com.software.pokedexV2.service.PokemonEquipoService;
import com.software.pokedexV2.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PokemonEquipoServiceImpl implements PokemonEquipoService {

    private final PokemonEquipoRepository pokemonEquipoRepository;
    private final EquipoService equipoService;
    private final PokemonService pokemonService;

    @Autowired
    public PokemonEquipoServiceImpl(
            PokemonEquipoRepository pokemonEquipoRepository,
            EquipoService equipoService,
            PokemonService pokemonService
    ) {
        this.pokemonEquipoRepository = pokemonEquipoRepository;
        this.equipoService = equipoService;
        this.pokemonService = pokemonService;
    }

    // CREATE
    @Override
    @Transactional
    public PokemonEquipoResponse addPokemonToTeam(PokemonEquipoRequest request) {

        EquipoResponse equipoResponse = equipoService.getTeamById(request.getIdEquipo());

        // 2. Obtener el Pokémon desde el SERVICE
        // Ajusta el nombre del método según tu PokemonService
        PokemonResponse pokemonResponse = pokemonService.obtenerPorNombre(request.getNombrePokemon());

        // 3. Convertir DTOs a entidades usando sus mappers 
        Equipo equipo = EquipoMapper.toEntity(equipoResponse);
        Pokemon pokemon = PokemonMapper.toEntity(pokemonResponse);

        // 4. (Opcional) Validar que no exista ya un Pokémon en esa posición del equipo
        pokemonEquipoRepository.findByEquipo_IdEquipoAndPosicion(
                        equipo.getIdEquipo(),
                        request.getPosicion()
                )
                .ifPresent(existing -> {
                    throw new RuntimeException(
                            "Ya existe un Pokémon en la posición " + request.getPosicion() + " para este equipo."
                    );
                });

        // 5. Mapear request -> entidad
        PokemonEquipo entity = PokemonEquipoMapper.toEntityCreate(
                request,
                equipo,
                pokemon
        );

        // 6. Guardar
        PokemonEquipo saved = pokemonEquipoRepository.save(entity);

        // 7. Entidad -> DTO
        return PokemonEquipoMapper.toDTO(saved);
    }

    // READ by ID
    @Override
    @Transactional(readOnly = true)
    public PokemonEquipoResponse getById(Long id) {
        PokemonEquipo entity = pokemonEquipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Registro pokemon_equipo no encontrado con id: " + id
                ));

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
    @Transactional
    public PokemonEquipoResponse updatePokemonInTeam(PokemonEquipoUpdateRequest request) {

        // 1. Buscar la entidad a actualizar
        PokemonEquipo entity = pokemonEquipoRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Registro pokemon_equipo no encontrado con id: " + request.getId()
                ));

        // 2. Si viene un nuevo nombre_pokemon, lo obtenemos desde el SERVICE
        Pokemon nuevoPokemon = null;
        if (request.getNombrePokemon() != null) {
            PokemonResponse pokemonResponse = pokemonService.obtenerPorNombre(request.getNombrePokemon());
            nuevoPokemon = PokemonMapper.toEntity(pokemonResponse);
        }

        // 3. Mapear campos del update request a la entidad existente
        PokemonEquipoMapper.toEntityUpdate(
                entity,
                request,
                nuevoPokemon
        );

        // 4. Guardar cambios
        PokemonEquipo updated = pokemonEquipoRepository.save(entity);

        // 5. Entidad -> DTO
        return PokemonEquipoMapper.toDTO(updated);
    }

    // DELETE
    @Override
    @Transactional
    public boolean removeFromTeam(Long id) {

        if (!pokemonEquipoRepository.existsById(id)) {
            return false;
        }

        pokemonEquipoRepository.deleteById(id);
        return true;
    }
}
