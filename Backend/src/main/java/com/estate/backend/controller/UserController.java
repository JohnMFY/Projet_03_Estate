package com.estate.backend.controller;

// DTO User
import com.estate.backend.dto.UserPayload;

// Service User
import com.estate.backend.service.UserService;

// Gestion des réponses HTTP
import org.springframework.http.ResponseEntity;

// Routes
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPayload> getUserById(@PathVariable Long id) {

        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}