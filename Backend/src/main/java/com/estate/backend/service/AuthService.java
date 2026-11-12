package com.estate.backend.service;

// DTO Auth
import com.estate.backend.dto.LoginInput;
import com.estate.backend.dto.RegisterInput;

// User et BDD
import com.estate.backend.entity.User;
import com.estate.backend.repository.UserRepository;

// Gestion auth / password
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

// Service Spring
import org.springframework.stereotype.Service;

// Dates
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
            .orElseThrow(() -> new BadCredentialsException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(input.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Email ou mot de passe incorrect");
        }

        return user;
    }

    public User register(RegisterInput input) {

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("Cette adresse e-mail est déjà utilisée");
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