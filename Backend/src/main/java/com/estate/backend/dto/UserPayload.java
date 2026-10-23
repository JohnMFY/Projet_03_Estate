package com.estate.backend.dto;

public record UserPayload(
    int id,
    String name,
    String email,
    String created_at,
    String updated_at
) {}