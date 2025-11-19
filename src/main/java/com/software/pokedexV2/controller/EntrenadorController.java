package com.software.pokedexV2.controller;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorUpdateRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.dto.response.GeneralResponse;
import com.software.pokedexV2.service.EntrenadorService;
import com.software.pokedexV2.utils.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pokedexV2/api/entrenador")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    @Autowired
    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    //CREATE (metodo aparte en sign up ↓ Este solo para probar api)
    @PostMapping()
    public ResponseEntity<GeneralResponse> createEntrenador(@RequestBody @Valid EntrenadorRequest entrenador){
        return ResponseBuilder.buildResponse(
                "Entrenador creado",
                HttpStatus.CREATED,
                entrenadorService.createEntrenador(entrenador)
        );
    }

    //READ
    @GetMapping()
    public ResponseEntity<GeneralResponse> getAllEntrenadores(){
        return ResponseBuilder.buildResponse(
                "Entrenadores encontrados",
                HttpStatus.OK,
                entrenadorService.getAll()
        );
    }

    @GetMapping("/{email}")
    public ResponseEntity<GeneralResponse> getEntrenadorByEmail(@PathVariable String email){
        return ResponseBuilder.buildResponse(
                "Entrenador encontrado",
                HttpStatus.OK,
                entrenadorService.getByEmail(email)
        );
    }

    @GetMapping("/region")
    public ResponseEntity<GeneralResponse> getAllEntrenadoresByRegion(@RequestParam String region){
        return ResponseBuilder.buildResponse(
                "Entrenadores encontrados",
                HttpStatus.OK,
                entrenadorService.getAllByRegionFav(region)
        );
    }

    @GetMapping("/tipo")
    public ResponseEntity<GeneralResponse> getAllEntrenadoresByTipo(@RequestParam String tipo){
        return ResponseBuilder.buildResponse(
                "Enrenadores encontrados",
                HttpStatus.OK,
                entrenadorService.getAllByTipoFav(tipo)
        );
    }

    @GetMapping("/pokemon")
    public ResponseEntity<GeneralResponse> getAllEntrenadoresByPokemon(@RequestParam String pokemon){
        return ResponseBuilder.buildResponse(
                "Entrenadores encontrados",
                HttpStatus.OK,
                entrenadorService.getAllByPokemonFav(pokemon)
        );
    }

    //UPDATE
    @PatchMapping()
    public ResponseEntity<GeneralResponse> updateEntrenador(@RequestBody EntrenadorUpdateRequest entrenadorUpdateRequest){
        EntrenadorResponse entrenadorResponse = entrenadorService.updateEntrenador(entrenadorUpdateRequest);
        return ResponseBuilder.buildResponse(
                "Entrenador actualizado",
                HttpStatus.OK,
                entrenadorResponse
        );
    }

    //DELETE
    @DeleteMapping("/id/{id}")
    public ResponseEntity<GeneralResponse> deleteEntrenadorById(@PathVariable Long id){
        return ResponseBuilder.buildResponse(
                "Entrenador eliminado",
                HttpStatus.OK,
                entrenadorService.deleteEntrenadorById(id)
        );
    }

    @DeleteMapping("/email/{email}")
    public ResponseEntity<GeneralResponse> deleteEntrenadorByEmail(@PathVariable String email){
        return ResponseBuilder.buildResponse(
                "Entrenador eliminado",
                HttpStatus.OK,
                entrenadorService.deleteEntrenadorByEmail(email)
        );
    }
}
