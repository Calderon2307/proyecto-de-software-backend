package com.software.pokedexV2.exception.Equipo;

public class TeamLimitException extends RuntimeException {
    public TeamLimitException(String message) {
        super(message);
    }
}
