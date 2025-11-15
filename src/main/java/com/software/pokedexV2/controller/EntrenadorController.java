package com.software.pokedexV2.controller;

import com.software.pokedexV2.service.EntrenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
