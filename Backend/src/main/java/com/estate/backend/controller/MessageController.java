package com.estate.backend.controller;

import com.estate.backend.dto.MessageInput;
import com.estate.backend.dto.MessageResponse;
import com.estate.backend.entity.User;
import com.estate.backend.service.MessageService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
                    "Message send with success"
                )
            ))
            .orElseGet(() ->
                ResponseEntity.notFound().build()
            );
    }
}