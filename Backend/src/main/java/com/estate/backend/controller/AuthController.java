package com.estate.backend.controller;

import com.estate.backend.dto.AuthPayload;
import com.estate.backend.dto.LoginInput;
import com.estate.backend.dto.UserPayload;
import com.estate.backend.entity.User;
import com.estate.backend.security.JwtService;
import com.estate.backend.service.AuthService;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @MutationMapping
    public AuthPayload login(@Argument LoginInput input) {

        User user = authService.authenticate(input);

        String token = jwtService.generateToken(user);

        return new AuthPayload(token);
    }

    @QueryMapping
    public UserPayload me(@AuthenticationPrincipal User user) {

        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }

        return new UserPayload(
            user.getId().intValue(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt() != null ? user.getCreatedAt().toString() : null,
            user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null
        );
    }
}