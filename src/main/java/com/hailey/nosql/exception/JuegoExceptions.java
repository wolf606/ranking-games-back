package com.hailey.nosql.exception;

import org.springframework.http.HttpStatus;

public class JuegoExceptions {

    public static class JuegoNotFoundException extends ApiException {
        public JuegoNotFoundException(String nombre) {
            super("Persona with nombre " + nombre + " not found", HttpStatus.NOT_FOUND);
        }
    }
}
