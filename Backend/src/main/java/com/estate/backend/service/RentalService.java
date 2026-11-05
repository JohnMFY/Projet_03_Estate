package com.estate.backend.service;

import com.estate.backend.dto.RentalPayload;
import com.estate.backend.entity.Rental;
import com.estate.backend.repository.RentalRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.estate.backend.dto.RentalCreateInput;
import com.estate.backend.entity.User;
import java.time.LocalDateTime;
import com.estate.backend.dto.RentalUpdateInput;
import org.springframework.security.access.AccessDeniedException;

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
    public Rental createRental(
        RentalCreateInput input,
        User owner
    ) {

        Rental rental = new Rental();

        rental.setName(input.getName());
        rental.setSurface(input.getSurface());
        rental.setPrice(input.getPrice());

        rental.setPicture(//min IO next
            input.getPicture().getOriginalFilename()
        );

        rental.setDescription(input.getDescription());
        rental.setOwner(owner);

        LocalDateTime now = LocalDateTime.now();

        rental.setCreatedAt(now);
        rental.setUpdatedAt(now);

        return rentalRepository.save(rental);
    }
    public Optional<Rental> updateRental(
    Long id,
    RentalUpdateInput input,
    User user
    ) {

        Optional<Rental> optionalRental = rentalRepository.findById(id);

        if (optionalRental.isEmpty()) {
            return Optional.empty();
        }

        Rental rental = optionalRental.get();

        if (!rental.getOwner().getId().equals(user.getId())) {
            throw new AccessDeniedException(
                "You are not allowed to update this rental"
            );
        }

        rental.setName(input.getName());
        rental.setSurface(input.getSurface());
        rental.setPrice(input.getPrice());
        rental.setDescription(input.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());

        return Optional.of(
            rentalRepository.save(rental)
        );
    }
}