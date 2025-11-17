package com.software.pokedexV2.controller;

import com.software.pokedexV2.dto.request.Equipo.EquipoRequest;
import com.software.pokedexV2.dto.request.Equipo.EquipoUpdateRequest;
import com.software.pokedexV2.dto.response.Equipo.EquipoResponse;
import com.software.pokedexV2.service.EquipoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipos")
public class EquipoController {

    private final EquipoService equipoService;

    @Autowired
    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    // CREATE
    @PostMapping("/create")
    public ResponseEntity<EquipoResponse> createTeam(
            @Valid @RequestBody EquipoRequest request
    ) {
        return ResponseEntity.ok(
                equipoService.createTeam(request)
        );
    }

    // READ
    // Get by team ID (solo para pruebas)
    @GetMapping("/{id}")
    public ResponseEntity<EquipoResponse> getTeamById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                equipoService.getTeamById(id)
        );
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<EquipoResponse>> getTeamsByTrainer(
            @PathVariable Long trainerId
    ) {
        return ResponseEntity.ok(
                equipoService.getTeamsByTrainerId(trainerId)
        );
    }

    @GetMapping("/trainer/{trainerId}/name")
    public ResponseEntity<EquipoResponse> getTeamByTrainerAndName(
            @PathVariable Long trainerId,
            @RequestParam("team") String teamName
    ) {
        return ResponseEntity.ok(
                equipoService.getTeamByTrainerIdAndName(trainerId, teamName)
        );
    }

    // UPDATE
    @PutMapping("/update")
    public ResponseEntity<EquipoResponse> updateTeam(
            @Valid @RequestBody EquipoUpdateRequest request
    ) {
        return ResponseEntity.ok(
                equipoService.updateTeam(request)
        );
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeam(
            @PathVariable Long id
    ) {
        boolean deleted = equipoService.deleteTeam(id);

        if (!deleted) {
            return ResponseEntity.status(404)
                    .body("Equipo no encontrado.");
        }

        return ResponseEntity.ok("Equipo eliminado correctamente.");
    }
}
