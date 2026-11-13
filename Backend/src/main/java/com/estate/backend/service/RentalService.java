package com.estate.backend.service;

// DTO Rental
import com.estate.backend.dto.RentalPayload;
import com.estate.backend.dto.RentalCreateInput;
import com.estate.backend.dto.RentalUpdateInput;

// Entity et BDD
import com.estate.backend.entity.Rental;
import com.estate.backend.entity.User;
import com.estate.backend.repository.RentalRepository;

// Service Spring
import org.springframework.stereotype.Service;

// Gestion des autorisations
import org.springframework.security.access.AccessDeniedException;

// Dates
import java.time.LocalDateTime;

// Liste et résultat présent ou vide
import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final S3Service s3Service;

    public RentalService(
        RentalRepository rentalRepository,
        S3Service s3Service
    ) {
        this.rentalRepository = rentalRepository;
        this.s3Service = s3Service;
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
            s3Service.getFileUrl(
                rental.getPicture()
            ),
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

       String pictureName = s3Service.uploadFile(
            input.getPicture()
        );

        rental.setPicture(pictureName);

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
                "Vous n'êtes pas autorisé à modifier cette location"
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