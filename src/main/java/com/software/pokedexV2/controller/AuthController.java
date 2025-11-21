package com.software.pokedexV2.controller;

import com.software.pokedexV2.dto.request.Entrenador.EntrenadorLoginRequest;
import com.software.pokedexV2.dto.request.Entrenador.EntrenadorRequest;
import com.software.pokedexV2.dto.response.Entrenador.EntrenadorResponse;
import com.software.pokedexV2.dto.response.GeneralResponse;
import com.software.pokedexV2.service.EntrenadorService;
import com.software.pokedexV2.utils.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v2/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EntrenadorService entrenadorService;

    @Autowired
    private JwtUtils jwtUtils;

    // --- REGISTRO CON TOKEN JWT ---
    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> registerEntrenador(@Valid @RequestBody EntrenadorRequest request) {

        // 1. Ejecutar el servicio y CAPTURAR el DTO de respuesta.
        EntrenadorResponse entrenadorResponse = entrenadorService.createEntrenador(request);

        // 2. Generar el token JWT con el email del nuevo entrenador
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        // 3. Construir el data response con el token
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("entrenador", entrenadorResponse);
        responseData.put("token", jwt);
        responseData.put("type", "Bearer");

        // 4. Retornar la respuesta completa
        return ResponseEntity.status(HttpStatus.CREATED).body(
                GeneralResponse.builder()
                        .message("Entrenador registrado exitosamente.")
                        .status(HttpStatus.CREATED.value())
                        .date(LocalDate.now())
                        .data(responseData)
                        .build()
        );
    }

    // --- LOGIN JWT ---
    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> authenticateEntrenador(@Valid @RequestBody EntrenadorLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("token", jwt);
        responseData.put("type", "Bearer");
        responseData.put("email", loginRequest.getEmail());

        return ResponseEntity.ok(
                GeneralResponse.builder()
                        .message("Login JWT exitoso.")
                        .data(responseData)
                        .status(HttpStatus.OK.value())
                        .build()
        );
    }
}