package com.example.api;

import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
public class CreateUser {
    @NotBlank
    private String username;
}
