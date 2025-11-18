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
import com.software.pokedexV2.dto.response.PokemonEquipo.PokemonEquipoDetalleResponse;


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


        PokemonResponse pokemonResponse = pokemonService.obtenerPorNombre(request.getNombrePokemon());

        Equipo equipo = EquipoMapper.toEntity(equipoResponse);
        Pokemon pokemon = PokemonMapper.toEntity(pokemonResponse);

        pokemonEquipoRepository.findByEquipo_IdEquipoAndPosicion(
                        equipo.getIdEquipo(),
                        request.getPosicion()
                )
                .ifPresent(existing -> {
                    throw new RuntimeException(
                            "Ya existe un Pokémon en la posición " + request.getPosicion() + " para este equipo."
                    );
                });


        PokemonEquipo entity = PokemonEquipoMapper.toEntityCreate(
                request,
                equipo,
                pokemon
        );

        PokemonEquipo saved = pokemonEquipoRepository.save(entity);

        return PokemonEquipoMapper.toDTO(saved);
    }

// READ by ID
@Override
@Transactional(readOnly = true)
public PokemonEquipoDetalleResponse getById(Long idEquipo) {


    EquipoResponse equipoResponse = equipoService.getTeamById(idEquipo);
    if (equipoResponse == null) {
        throw new RuntimeException("Equipo no encontrado con id: " + idEquipo);
    }

    List<PokemonEquipo> lista = pokemonEquipoRepository.findByEquipo_IdEquipo(idEquipo);


    List<PokemonEquipoResponse> pokemones = PokemonEquipoMapper.toDTOList(lista);


    return PokemonEquipoDetalleResponse.builder()
            .idEquipo(equipoResponse.getIdEquipo())
            .nombreEquipo(equipoResponse.getNombreEquipo())
            .pokemones(pokemones)
            .build();
}



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

    
        PokemonEquipo entity = pokemonEquipoRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Registro pokemon_equipo no encontrado con id: " + request.getId()
                ));

   
        Pokemon nuevoPokemon = null;
        if (request.getNombrePokemon() != null) {
            PokemonResponse pokemonResponse = pokemonService.obtenerPorNombre(request.getNombrePokemon());
            nuevoPokemon = PokemonMapper.toEntity(pokemonResponse);
        }

      
        PokemonEquipoMapper.toEntityUpdate(
                entity,
                request,
                nuevoPokemon
        );


        PokemonEquipo updated = pokemonEquipoRepository.save(entity);

        return PokemonEquipoMapper.toDTO(updated);
    }

    // DELETE
@Override
@Transactional
public boolean removeFromTeam(Long idEquipo, String nombrePokemon, Integer posicion) {

    EquipoResponse equipoResponse = equipoService.getTeamById(idEquipo);
    if (equipoResponse == null) {
        return false; 
    }

    PokemonEquipo entity = pokemonEquipoRepository
            .findByEquipo_IdEquipoAndPosicion(idEquipo, posicion)
            .orElse(null);

    if (entity == null) {
        return false;
    }

    if (entity.getPokemon() == null
            || entity.getPokemon().getNombre() == null
            || !entity.getPokemon().getNombre().equalsIgnoreCase(nombrePokemon)) {

        return false;
    }

    pokemonEquipoRepository.delete(entity);
    return true;
}

}
