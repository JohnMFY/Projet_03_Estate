package com.estate.backend.controller;

// DTO Rentals
import com.estate.backend.dto.RentalsPayload;
import com.estate.backend.dto.RentalPayload;
import com.estate.backend.dto.RentalResponse;

// Création Rental
import com.estate.backend.dto.RentalCreateInput;

// Update Rental
import com.estate.backend.dto.RentalUpdateInput;

// User auth
import com.estate.backend.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

// Service Rental
import com.estate.backend.service.RentalService;

// Validation data
import jakarta.validation.Valid;

// Gestion des réponses HTTP
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

// Routes
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RentalResponse> createRental(
        @Valid @ModelAttribute RentalCreateInput input,
        @AuthenticationPrincipal User user
    ) {

        rentalService.createRental(input, user);

        return ResponseEntity.ok(
            new RentalResponse("Rental created")
        );
    }
    @PutMapping(
    value = "/{id}",
    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<RentalResponse> updateRental(
        @PathVariable Long id,
        @Valid @ModelAttribute RentalUpdateInput input,
        @AuthenticationPrincipal User user
    ) {

        return rentalService.updateRental(id, input, user)
            .map(rental -> ResponseEntity.ok(
                new RentalResponse("Rental updated")
            ))
            .orElseGet(() ->
                ResponseEntity.notFound().build()
            );
    }
}