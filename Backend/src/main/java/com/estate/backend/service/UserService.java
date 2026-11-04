package com.estate.backend.service;

import com.estate.backend.dto.UserPayload;
import com.estate.backend.entity.User;
import com.estate.backend.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserPayload> getUserById(Long id) {

        return userRepository.findById(id)
            .map(this::toPayload);
    }

    private UserPayload toPayload(User user) {

        return new UserPayload(
            user.getId().intValue(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt() != null ? user.getCreatedAt().toString() : null,
            user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null
        );
    }
}