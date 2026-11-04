package com.estate.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageInput {

    @NotNull
    private Long rental_id;

    @NotNull
    private Long user_id;

    @NotBlank
    private String message;

    public MessageInput() {
    }

    public Long getRental_id() {
        return rental_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setRental_id(Long rental_id) {
        this.rental_id = rental_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}