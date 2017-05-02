package com.example.api;

import com.example.api.helper.JsonResource;
import com.example.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserData extends JsonResource {
    private User user;

    public UserData(User user) {
        this.user = user;
    }

    public String getId() {
        return user.getId();
    }

    @JsonProperty("username")
    public String getUserName() {
        return user.getUsername();
    }
}
