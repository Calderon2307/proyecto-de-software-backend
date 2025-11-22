package com.software.pokedexV2.controller;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.dto.response.GeneralResponse;
import com.software.pokedexV2.service.EquipoService;
import com.software.pokedexV2.utils.ResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v2/equipo")
public class EquipoController {

    private final EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createTeam(@Valid @RequestBody EquipoRequest request) {
        return ResponseBuilder.buildResponse(
                "Equipo creado correctamente",
                HttpStatus.CREATED,
                equipoService.createTeam(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GeneralResponse> getTeamById(@PathVariable Long id) {
        return ResponseBuilder.buildResponse(
                "Equipo encontrado",
                HttpStatus.OK,
                equipoService.getTeamById(id)
        );
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<GeneralResponse> getTeamsByTrainer(@PathVariable Long trainerId) {
        return ResponseBuilder.buildResponse(
                "Equipos encontrados",
                HttpStatus.OK,
                equipoService.getTeamsByTrainerId(trainerId)
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GeneralResponse> updateTeam(
            @Valid @RequestBody EquipoUpdateRequest request,
            @PathVariable Long id
    ) {
        return ResponseBuilder.buildResponse(
                "Equipo actualizado",
                HttpStatus.OK,
                equipoService.updateTeam(id, request)
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GeneralResponse> deleteTeam(@PathVariable Long id) {
        return ResponseBuilder.buildResponse(
                "Equipo eliminado",
                HttpStatus.OK,
                equipoService.deleteTeam(id)
        );
    }
}
