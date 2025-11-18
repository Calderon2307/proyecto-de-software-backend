package com.software.pokedexV2.controller;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoRequest;
import com.software.pokedexV2.dto.request.PokemonEquipo.PokemonEquipoUpdateRequest;
import com.software.pokedexV2.dto.response.GeneralResponse;
import com.software.pokedexV2.service.PokemonEquipoService;
import com.software.pokedexV2.utils.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//pokemon
@RestController
@RequestMapping("/pokedexV2/api/pokemon-equipo")
public class PokemonEquipoController {

    private final PokemonEquipoService pokemonEquipoService;

    @Autowired
    public PokemonEquipoController(PokemonEquipoService pokemonEquipoService) {
        this.pokemonEquipoService = pokemonEquipoService;
    }

    // CREATE – agregar un Pokémon a un equipo
    @PostMapping()
    public ResponseEntity<GeneralResponse> addPokemonToTeam(
            @RequestBody @Valid PokemonEquipoRequest request
    ) {
        return ResponseBuilder.buildResponse(
                "Pokémon agregado al equipo",
                HttpStatus.CREATED,
                pokemonEquipoService.addPokemonToTeam(request)
        );
    }

    // READ – obtener un registro específico de pokemon_equipo por su id
    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getById(@PathVariable Long id) {
        return ResponseBuilder.buildResponse(
                "Registro pokemon_equipo encontrado",
                HttpStatus.OK,
                pokemonEquipoService.getById(id)
        );
    }

    @GetMapping("/equipo/{idEquipo}")
    public ResponseEntity<GeneralResponse> getByTeamId(@PathVariable Long idEquipo) {
        return ResponseBuilder.buildResponse(
                "Pokémon del equipo encontrados",
                HttpStatus.OK,
                pokemonEquipoService.getByTeamId(idEquipo)
        );
    }

    // UPDATE – actualizar posición o cambiar Pokémon dentro del equipo
    @PatchMapping()
    public ResponseEntity<GeneralResponse> updatePokemonInTeam(
            @RequestBody @Valid PokemonEquipoUpdateRequest request
    ) {
        return ResponseBuilder.buildResponse(
                "Registro pokemon_equipo actualizado",
                HttpStatus.OK,
                pokemonEquipoService.updatePokemonInTeam(request)
        );
    }

    // DELETE – eliminar un Pokémon del equipon
@DeleteMapping("/equipo/{idEquipo}")
public ResponseEntity<GeneralResponse> deleteFromTeam(
        @PathVariable Long idEquipo,
        @RequestParam("nombre_pokemon") String nombrePokemon,
        @RequestParam("posicion") Integer posicion
) {
    boolean deleted = pokemonEquipoService.removeFromTeam(idEquipo, nombrePokemon, posicion);

    return ResponseBuilder.buildResponse(
            deleted
                    ? "Pokémon eliminado del equipo"
                    : "No se encontró un Pokémon que coincida con el equipo, nombre y posición especificados",
            deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND,
            deleted
    );
}

}
