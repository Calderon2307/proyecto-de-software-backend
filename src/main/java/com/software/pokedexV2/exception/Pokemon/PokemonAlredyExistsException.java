package com.software.pokedexV2.exception.Pokemon;

public class PokemonAlredyExistsException extends RuntimeException {
    public PokemonAlredyExistsException(String message) {
        super(message);
    }
}
