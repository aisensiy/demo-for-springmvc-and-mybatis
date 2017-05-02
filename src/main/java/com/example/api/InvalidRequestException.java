package com.example.api;

import org.springframework.validation.Errors;


@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {
    private final Errors errors;

    public InvalidRequestException(String message, Errors errors) {
        super(message);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
