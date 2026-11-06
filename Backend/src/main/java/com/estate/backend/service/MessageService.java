package com.estate.backend.service;

// DTO Message
import com.estate.backend.dto.MessageInput;

// Entity
import com.estate.backend.entity.Message;
import com.estate.backend.entity.Rental;
import com.estate.backend.entity.User;

// Repository
import com.estate.backend.repository.MessageRepository;
import com.estate.backend.repository.RentalRepository;

// Service Spring
import org.springframework.stereotype.Service;

// Dates
import java.time.LocalDateTime;

// Gestion retour resultat vide ou présent
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;

    public MessageService(
        MessageRepository messageRepository,
        RentalRepository rentalRepository
    ) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
    }

    public Optional<Message> sendMessage(
        MessageInput input,
        User user
    ) {

        Optional<Rental> rental = rentalRepository.findById(
            input.getRental_id()
        );

        if (rental.isEmpty()) {
            return Optional.empty();
        }

        Message message = new Message();

        message.setMessage(input.getMessage());
        message.setUser(user);
        message.setRental(rental.get());

        LocalDateTime now = LocalDateTime.now();

        message.setCreatedAt(now);
        message.setUpdatedAt(now);

        return Optional.of(
            messageRepository.save(message)
        );
    }
}