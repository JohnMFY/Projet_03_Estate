package com.estate.backend.controller;

import com.estate.backend.dto.RentalsPayload;
import com.estate.backend.service.RentalService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.estate.backend.dto.RentalPayload;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<RentalsPayload> getAllRentals() {

        return ResponseEntity.ok(
            new RentalsPayload(
                rentalService.getAllRentals()
            )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalPayload> getRentalById(@PathVariable Long id) {

        return rentalService.getRentalById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}