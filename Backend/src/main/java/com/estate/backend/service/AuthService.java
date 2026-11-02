package com.estate.backend.service;

import com.estate.backend.dto.LoginInput;
import com.estate.backend.entity.User;
import com.estate.backend.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.estate.backend.dto.RegisterInput;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(LoginInput input) {

        User user = userRepository.findByEmail(input.getEmail())
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        return user;
    }

    public User register(RegisterInput input) {

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();

        user.setName(input.getName());
        user.setEmail(input.getEmail());

        user.setPassword(
            passwordEncoder.encode(input.getPassword())
        );

        LocalDateTime now = LocalDateTime.now();

        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        return userRepository.save(user);
    }
}