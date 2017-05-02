package com.example.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/users")
public class UsersApi {
    @RequestMapping(method = GET)
    public List<UserData> getUsers() {
        return new ArrayList<>();
    }

    @RequestMapping(method = POST)
    public ResponseEntity createUser(@Valid @RequestBody CreateUser createUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Error in create user", bindingResult);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
