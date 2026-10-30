package com.estate.backend.controller;

import com.estate.backend.dto.AuthPayload;
import com.estate.backend.dto.LoginInput;
import com.estate.backend.entity.User;
import com.estate.backend.security.JwtService;
import com.estate.backend.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estate.backend.dto.UserPayload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthPayload> login(@RequestBody LoginInput input) {

        User user = authService.authenticate(input);

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new AuthPayload(token));
    }
    @GetMapping("/me")
    public ResponseEntity<UserPayload> me(@AuthenticationPrincipal User user) {

        UserPayload payload = new UserPayload(
            user.getId().intValue(),
            user.getName(),
            user.getEmail(),
            user.getCreatedAt() != null ? user.getCreatedAt().toString() : null,
            user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null
        );

        return ResponseEntity.ok(payload);
    }
}
