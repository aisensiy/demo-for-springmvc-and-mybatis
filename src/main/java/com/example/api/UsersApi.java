package com.example.api;

import com.example.api.exception.InvalidRequestException;
import com.example.domain.User;
import com.example.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/users")
public class UsersApi {
    private UserRepository userRepository;

    @Autowired
    public UsersApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = GET)
    public List<UserData> getUsers() {
        return new ArrayList<>();
    }

    @RequestMapping(method = POST)
    public ResponseEntity createUser(@Valid @RequestBody CreateUser createUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Error in create user", bindingResult);
        }

        if (userRepository.findByUsername(createUser.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "DUPLICATED", "duplicated username");
            throw new InvalidRequestException("Error in create user", bindingResult);
        }
        User user = new User(UUID.randomUUID().toString(), createUser.getUsername());
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
