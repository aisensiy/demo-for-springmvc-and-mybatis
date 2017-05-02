package com.example.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
public class ErrorResource {
    private String code;
    private String message;
    private List<FieldErrorResource> fieldErrors;

    public ErrorResource(String invalidRequest, String message, List<FieldErrorResource> fieldErrorResources) {

        this.code = invalidRequest;
        this.message = message;
        this.fieldErrors = fieldErrorResources;
    }
}
