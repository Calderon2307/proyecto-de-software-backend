package com.software.pokedexV2.service.impl;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.response.Equipo.EntrenadorEquiposResponse;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.dto.response.Equipo.EquipoSummaryResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonEquipoResponse;
import com.software.pokedexV2.entities.Equipo;
import com.software.pokedexV2.entities.Entrenador;
import com.software.pokedexV2.entities.Pokemon;
import com.software.pokedexV2.entities.PokemonEquipo;
import com.software.pokedexV2.exception.Entrenador.EntrenadorNotFoundException;
import com.software.pokedexV2.exception.Equipo.EquipoAlredyExistsException;
import com.software.pokedexV2.exception.Equipo.EquipoNotFoundException;
import com.software.pokedexV2.exception.Equipo.PokemonLimitException;
import com.software.pokedexV2.exception.Equipo.TeamLimitException;
import com.software.pokedexV2.exception.Pokemon.PokemonNotFoundException;
import com.software.pokedexV2.mapper.EntrenadorMapper;
import com.software.pokedexV2.mapper.EquipoMapper;
import com.software.pokedexV2.mapper.PokemonMapper;
import com.software.pokedexV2.repository.EntrenadorRepository;
import com.software.pokedexV2.repository.EquipoRepository;
import com.software.pokedexV2.repository.PokemonEquipoRepository;
import com.software.pokedexV2.repository.PokemonRepository;
import com.software.pokedexV2.service.EquipoService;
import com.software.pokedexV2.service.EntrenadorService;
import com.software.pokedexV2.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {

    private final EquipoRepository equipoRepository;
    private final PokemonEquipoRepository pokemonEquipoRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final PokemonRepository pokemonRepository;
    private final PokemonService pokemonService;

    @Autowired
    public EquipoServiceImpl(
            EquipoRepository equipoRepository,
            PokemonEquipoRepository pokemonEquipoRepository,
            EntrenadorRepository entrenadorRepository,
            PokemonRepository pokemonRepository,
            PokemonService pokemonService
    ) {
        this.equipoRepository = equipoRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.pokemonEquipoRepository = pokemonEquipoRepository;
        this.pokemonRepository = pokemonRepository;
        this.pokemonService = pokemonService;
    }

    // CREATE
    @Override
    @Transactional
    public EquipoResponse createTeam(EquipoRequest request) {
        Entrenador entrenador = entrenadorRepository.findById(request.getIdEntrenador()).orElseThrow(
                () -> new EntrenadorNotFoundException("Entrenador no encontrado")
        );

        String nombreNormalizado = request.getNombreEquipo().toLowerCase().trim();

        boolean equipoExists = equipoRepository.existsByNombreEquipoAndEntrenador_Id(
                nombreNormalizado,
                entrenador.getId()
        );

        if (equipoExists) throw new EquipoAlredyExistsException("Ya hy un equipo con ese nombre.");

        if (request.getEquipoPokemon().isEmpty() || request.getEquipoPokemon().size() > 6) {
            throw new PokemonLimitException("Un equipo debe tener entre 1 y 6 Pokémon.");
        }

        int equiposActuales = equipoRepository.countByEntrenador_Id(request.getIdEntrenador());

        if (equiposActuales >= 6) {
            throw new TeamLimitException("El entrenador ya tiene el máximo de 6 equipos.");
        }

        request.setNombreEquipo(nombreNormalizado);

        Equipo equipoGuardado = equipoRepository.save(EquipoMapper.toEntityCreate(request, entrenador));
        List<PokemonEquipoResponse> equipoPokemon = new ArrayList<>();

        for (int i = 0; i < request.getEquipoPokemon().size(); i++) {

            PokemonRequest poke = request.getEquipoPokemon().get(i);
            String nombrePokemon = poke.getNombre().toLowerCase();

            Pokemon pokemon;

            boolean pokemonExists = pokemonRepository.existsByNombre(nombrePokemon);

            if (!pokemonExists) {
                pokemonService.createPokemon(poke);
            }
            pokemon = pokemonRepository.findByNombre(nombrePokemon).orElseThrow(
                    () -> new PokemonNotFoundException("Pokemon no encontrado")
            );

            pokemonEquipoRepository.save(PokemonEquipo.builder()
                    .equipo(equipoGuardado)
                    .pokemon(pokemon)
                    .posicion(i+1)
                    .build()
            );

            equipoPokemon.add(PokemonMapper.toEquipoResponseDTO(
                    pokemon,
                    i + 1
            ));
        }

        return EquipoMapper.toDTO(equipoGuardado, equipoPokemon);
    }

    // READ
    @Override
    public EquipoResponse getTeamById(Long id) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado."));

        List<PokemonEquipo> equipos = pokemonEquipoRepository.findByEquipo_IdEquipoOrderByPosicion(equipo.getIdEquipo());

        List<PokemonEquipoResponse> equipoPokemon = new ArrayList<>();
        for (PokemonEquipo pokemonEquipo : equipos) {
            equipoPokemon.add(
                    PokemonMapper.toEquipoResponseDTO(
                            pokemonEquipo.getPokemon(),
                            pokemonEquipo.getPosicion()
                    )
            );
        }

        return EquipoMapper.toDTO(equipo, equipoPokemon);
    }

    @Override
    public EntrenadorEquiposResponse getTeamsByTrainerId(Long trainerId) {
        Entrenador entrenador = entrenadorRepository.findById(trainerId).orElseThrow(
                () -> new EntrenadorNotFoundException("Entrenador no encontrado")
        );

        List<Equipo> equipoEntities = equipoRepository.findAllByEntrenador_Id(trainerId);
        List<EquipoSummaryResponse> equiposDeEntrenador = new ArrayList<>();

        for (Equipo equipo : equipoEntities) {
            List<PokemonEquipo> pokemonEquipoEntities = pokemonEquipoRepository.findByEquipo_IdEquipoOrderByPosicion(equipo.getIdEquipo());
            List<PokemonEquipoResponse> equipoPokemon = new ArrayList<>();
            for (PokemonEquipo pokemonEquipo : pokemonEquipoEntities) {
                equipoPokemon.add(
                        PokemonMapper.toEquipoResponseDTO(
                                pokemonEquipo.getPokemon(),
                                pokemonEquipo.getPosicion()
                        )
                );
            }
            equiposDeEntrenador.add(EquipoMapper.toSummaryDTO(equipo, equipoPokemon));
        }

        return EquipoMapper.toEntrenadorEquiposDTO(EntrenadorMapper.toSummaryDTO(entrenador), equiposDeEntrenador);

    }

    @Override
    public List<EquipoResponse> getAllTeams() {
        List<Equipo> equipos = equipoRepository.findAll();
        List<EquipoResponse> todosLosEquipos = new ArrayList<>();

        for (Equipo equipo : equipos) {

            List<PokemonEquipo> pokemonEquipoEntities =
                    pokemonEquipoRepository.findByEquipo_IdEquipoOrderByPosicion(equipo.getIdEquipo());

            List<PokemonEquipoResponse> equipoPokemon = new ArrayList<>();

            for (PokemonEquipo pokemonEquipo : pokemonEquipoEntities) {
                equipoPokemon.add(
                        PokemonMapper.toEquipoResponseDTO(
                                pokemonEquipo.getPokemon(),
                                pokemonEquipo.getPosicion()
                        )
                );
            }

            todosLosEquipos.add(
                    EquipoMapper.toDTO(equipo, equipoPokemon)
            );
        }

        return todosLosEquipos;
    }

    // UPDATE
    @Override
    @Transactional
    public EquipoResponse updateTeam(Long idEquipo, EquipoUpdateRequest request) {

        Equipo equipo = equipoRepository.findById(idEquipo)
                .orElseThrow(() -> new EquipoNotFoundException("Equipo no encontrado"));

        if (request.getEquipoPokemon().isEmpty() || request.getEquipoPokemon().size() > 6) {
            throw new PokemonLimitException("Un equipo debe tener entre 1 y 6 Pokémon.");
        }

        equipo.setNombreEquipo(request.getNombreEquipo().toLowerCase());
        equipoRepository.save(equipo);

        pokemonEquipoRepository.deleteByEquipo_IdEquipo(idEquipo);

        List<PokemonEquipoResponse> equipoPokemon = new ArrayList<>();
        for (int i = 0; i < request.getEquipoPokemon().size(); i++) {

            PokemonRequest poke = request.getEquipoPokemon().get(i);
            String nombrePokemon = poke.getNombre().toLowerCase();

            Pokemon pokemon;

            boolean pokemonExists = pokemonRepository.existsByNombre(nombrePokemon);

            if (!pokemonExists) {
                pokemonService.createPokemon(poke);
            }
            pokemon = pokemonRepository.findByNombre(nombrePokemon).orElseThrow(
                    () -> new PokemonNotFoundException("Pokemon no encontrado")
            );

            pokemonEquipoRepository.save(PokemonEquipo.builder()
                    .equipo(equipo)
                    .pokemon(pokemon)
                    .posicion(i+1)
                    .build()
            );

            equipoPokemon.add(PokemonMapper.toEquipoResponseDTO(
                    pokemon,
                    i + 1
            ));
        }

        return EquipoMapper.toDTO(equipo, equipoPokemon);
    }

    // DELETE
    @Override
    @Transactional
    public EquipoResponse deleteTeam(Long id) {

        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado."));

        List<PokemonEquipo> pokemonEquipoEntities =
                pokemonEquipoRepository.findByEquipo_IdEquipoOrderByPosicion(id);

        List<PokemonEquipoResponse> equipoPokemon = new ArrayList<>();

        for (PokemonEquipo pe : pokemonEquipoEntities) {
            equipoPokemon.add(
                    PokemonMapper.toEquipoResponseDTO(
                            pe.getPokemon(),
                            pe.getPosicion()
                    )
            );
        }

        pokemonEquipoRepository.deleteByEquipo_IdEquipo(id);

        equipoRepository.delete(equipo);

        return EquipoMapper.toDTO(equipo, equipoPokemon);
    }
}
