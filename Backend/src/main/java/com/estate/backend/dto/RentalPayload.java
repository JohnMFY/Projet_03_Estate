package com.estate.backend.dto;

public record RentalPayload(
    int id,
    String name,
    double surface,
    double price,
    String picture,
    String description,
    int owner_id,
    String created_at,
    String updated_at
) {
}