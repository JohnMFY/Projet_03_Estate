package com.estate.backend.dto;

import java.util.List;

public record RentalsPayload(
    List<RentalPayload> rentals
) {
}