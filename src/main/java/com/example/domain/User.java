package com.example.domain;

import lombok.Data;

import java.util.Objects;

@Data
public class User {
    private final String id;
    private final String username;

    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }
}
