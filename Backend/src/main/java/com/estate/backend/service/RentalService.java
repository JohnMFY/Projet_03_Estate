package com.estate.backend.service;

import com.estate.backend.dto.RentalPayload;
import com.estate.backend.entity.Rental;
import com.estate.backend.repository.RentalRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<RentalPayload> getAllRentals() {

        return rentalRepository.findAll()
            .stream()
            .map(this::toPayload)
            .toList();
    }

    private RentalPayload toPayload(Rental rental) {

        return new RentalPayload(
            rental.getId().intValue(),
            rental.getName(),
            rental.getSurface(),
            rental.getPrice(),
            rental.getPicture(),
            rental.getDescription(),
            rental.getOwner().getId().intValue(),
            rental.getCreatedAt() != null ? rental.getCreatedAt().toString() : null,
            rental.getUpdatedAt() != null ? rental.getUpdatedAt().toString() : null
        );
    }
    
    public Optional<RentalPayload> getRentalById(Long id) {

        return rentalRepository.findById(id)
            .map(this::toPayload);
    }
}