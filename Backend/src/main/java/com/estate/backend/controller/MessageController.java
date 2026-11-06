package com.estate.backend.controller;

// DTO message
import com.estate.backend.dto.MessageInput;
import com.estate.backend.dto.MessageResponse;

// User auth
import com.estate.backend.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

// Service messages
import com.estate.backend.service.MessageService;

// Validation data
import jakarta.validation.Valid;

// Gestion des réponses HTTP
import org.springframework.http.ResponseEntity;

// Routes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
        @Valid @RequestBody MessageInput input,
        @AuthenticationPrincipal User user
    ) {

        return messageService.sendMessage(input, user)
            .map(message -> ResponseEntity.ok(
                new MessageResponse(
                    "Message OK"
                )
            ))
            .orElseGet(() ->
                ResponseEntity.notFound().build()
            );
    }
}