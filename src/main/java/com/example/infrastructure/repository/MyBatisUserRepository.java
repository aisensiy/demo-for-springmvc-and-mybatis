package com.example.infrastructure.repository;

import com.example.domain.User;
import com.example.domain.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyBatisUserRepository implements UserRepository {
    @Override
    public void save(User user) {

    }

    @Override
    public Optional<User> findById(String userId) {
        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return null;
    }
}
