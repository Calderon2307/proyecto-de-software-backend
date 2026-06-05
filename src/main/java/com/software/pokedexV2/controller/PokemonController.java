package com.software.pokedexV2.controller;

import com.software.pokedexV2.dto.request.Pokemon.PokemonRequest;
import com.software.pokedexV2.dto.request.Pokemon.PokemonUpdateRequest;
import com.software.pokedexV2.dto.response.GeneralResponse;
import com.software.pokedexV2.dto.response.Pokemon.PokemonResponse;
import com.software.pokedexV2.service.PokemonService;
import com.software.pokedexV2.utils.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    // CREATE
    @PostMapping()
    public ResponseEntity<GeneralResponse> createPokemon(
            @RequestBody @Valid PokemonRequest pokemonRequest
    ) {
        PokemonResponse creado = pokemonService.createPokemon(pokemonRequest);

        return ResponseBuilder.buildResponse(
                "Pokémon creado",
                HttpStatus.CREATED,
                creado
        );
    }

    // READ - todos los pokémon
    @GetMapping()
    public ResponseEntity<GeneralResponse> getAllPokemons() {
        List<PokemonResponse> pokemons = pokemonService.getAll();

        return ResponseBuilder.buildResponse(
                "Pokémon encontrados",
                HttpStatus.OK,
                pokemons
        );
    }

    // READ - por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<GeneralResponse> getPokemonByNombre(
            @PathVariable String nombre
    ) {
        PokemonResponse pokemon = pokemonService.obtenerPorNombre(nombre);

        return ResponseBuilder.buildResponse(
                "Pokémon encontrado",
                HttpStatus.OK,
                pokemon
        );
    }

    // UPDATE (PATCH)
    @PatchMapping()
    public ResponseEntity<GeneralResponse> updatePokemon(
            @RequestBody @Valid PokemonUpdateRequest pokemonUpdateRequest
    ) {
        PokemonResponse actualizado = pokemonService.updatePokemon(pokemonUpdateRequest);

        return ResponseBuilder.buildResponse(
                "Pokémon actualizado",
                HttpStatus.OK,
                actualizado
        );
    }

    // DELETE - por id
    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse> deletePokemonById(
            @PathVariable Long id
    ) {
        PokemonResponse eliminado = pokemonService.deletePokemonById(id);

        return ResponseBuilder.buildResponse(
                "Pokémon eliminado",
                HttpStatus.OK,
                eliminado
        );
    }

    // DELETE - por nombre
    @DeleteMapping("/nombre/{nombre}")
    public ResponseEntity<GeneralResponse> deletePokemonByNombre(
            @PathVariable String nombre
    ) {
        PokemonResponse eliminado = pokemonService.deletePokemonByNombre(nombre);

        return ResponseBuilder.buildResponse(
                "Pokémon eliminado",
                HttpStatus.OK,
                eliminado
        );
    }
}
