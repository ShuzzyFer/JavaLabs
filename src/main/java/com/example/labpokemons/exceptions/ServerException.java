package com.example.labpokemons.exceptions;

public class ServerException extends RuntimeException {
    public ServerException(final String message) {
        super(message);
    }
}
