package com.example.api;

import com.example.domain.User;
import com.example.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users/{userId}")
public class UserApi {
    private UserRepository userRepository;

    @Autowired
    public UserApi(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<UserData> getUser(@PathVariable("userId") String userId) {
        User user = userRepository.findById(userId).get();
        UserData userData = new UserData(user);
        userData.addLink("self", linkTo(methodOn(UserApi.class).getUser(userId)).toString());
        return new ResponseEntity<>(userData, HttpStatus.OK);
    }
}
