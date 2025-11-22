package com.software.pokedexV2.exception;

import com.software.pokedexV2.dto.response.ApiErrorResponse;
import com.software.pokedexV2.exception.Entrenador.EntrenadorAlredyExistsException;
import com.software.pokedexV2.exception.Entrenador.EntrenadorMismatchedCredentialsException;
import com.software.pokedexV2.exception.Entrenador.EntrenadorNotFoundException;
import com.software.pokedexV2.exception.Equipo.EquipoAlredyExistsException;
import com.software.pokedexV2.exception.Equipo.EquipoNotFoundException;
import com.software.pokedexV2.exception.Pokemon.PokemonAlredyExistsException;
import com.software.pokedexV2.exception.Pokemon.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Excepciones de Entrenador
    @ExceptionHandler(EntrenadorAlredyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEntrenadorAlredyExistsException(EntrenadorAlredyExistsException e) {
        return buildErrorResponse(e, HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(EntrenadorNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntrenadorNotFoundException(EntrenadorNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EntrenadorMismatchedCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleEntrenadorMismatchedCredentialsException(EntrenadorMismatchedCredentialsException e) {
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    //Excepciones de Pokemon
    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlePokemonNotFoundException(PokemonNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(PokemonAlredyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handlePokemonAlreadyExistsException(PokemonAlredyExistsException e) {
        return buildErrorResponse(e, HttpStatus.CONFLICT, e.getMessage());
    }

    //Excepciones de Equipo
    @ExceptionHandler(EquipoNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEquipoNotFoundException(EquipoNotFoundException e) {
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(EquipoAlredyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleEquipoAlreadyExistsException(EquipoAlredyExistsException e) {
        return buildErrorResponse(e, HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValueOfEntity(
            MethodArgumentNotValidException e
    ){
        List<String> errors = e.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, errors);
    }

    public ResponseEntity<ApiErrorResponse> buildErrorResponse(
        Exception e,
        HttpStatus status,
        Object data
    ){
        String uri = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        return ResponseEntity.status(status).body(ApiErrorResponse.builder()
                .data(data)
                .status(status.value())
                .date(LocalDate.now())
                .uri(uri)
                .build()
        );
    }
}
