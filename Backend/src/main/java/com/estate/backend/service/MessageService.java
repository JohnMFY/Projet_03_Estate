package com.estate.backend.service;

import com.estate.backend.dto.MessageInput;
import com.estate.backend.entity.Message;
import com.estate.backend.entity.Rental;
import com.estate.backend.entity.User;
import com.estate.backend.repository.MessageRepository;
import com.estate.backend.repository.RentalRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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