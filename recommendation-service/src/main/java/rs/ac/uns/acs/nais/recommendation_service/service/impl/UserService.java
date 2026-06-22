package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.recommendation_service.dto.*;
import rs.ac.uns.acs.nais.recommendation_service.model.User;
import rs.ac.uns.acs.nais.recommendation_service.repository.ArrangementRepository;
import rs.ac.uns.acs.nais.recommendation_service.repository.UserRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IUserService;

import java.util.List;
import java.util.Optional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import rs.ac.uns.acs.nais.recommendation_service.config.RabbitMQConfig;
import rs.ac.uns.acs.nais.recommendation_service.messaging.event.BookingCreatedEvent;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ArrangementRepository arrangementRepository;
    private final RabbitTemplate rabbitTemplate;
    public UserService(
            UserRepository userRepository,
            ArrangementRepository arrangementRepository,
            RabbitTemplate rabbitTemplate
    ) {
        this.userRepository = userRepository;
        this.arrangementRepository = arrangementRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    public Integer getBookedCount(Long userId, Long arrangementId) {
        return userRepository.getBookedCount(userId, arrangementId);
    }
    public String addOrUpdateBookedSaga(
            Long reservationId,
            Long userId,
            Long arrangementId,
            String customerName,
            Integer persons,
            Double totalPrice
    ) {
        String sagaId = UUID.randomUUID().toString();

        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }

        String arrangementName =
                arrangementRepository.findArrangementNameById(arrangementId);

        if (arrangementName == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        String destinationName =
                arrangementRepository.findDestinationNameByArrangementId(arrangementId);

        if (destinationName == null) {
            destinationName = "Nepoznato";
        }

        userRepository.addOrUpdateBooked(userId, arrangementId, persons, totalPrice);



        Integer countAfterIncrease = userRepository.getBookedCount(userId, arrangementId);
        System.out.println("COUNT POSLE NEO4J UPISA = " + countAfterIncrease);

        BookingCreatedEvent event = new BookingCreatedEvent(
                sagaId,
                reservationId,
                userId,
                arrangementId,
                arrangementName,
                destinationName,
                customerName,
                persons,
                totalPrice
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.BOOKING_CREATED_KEY,
                event
        );

        return sagaId;
    }

    public void compensateBookedRelationship(Long userId, Long arrangementId) {
        userRepository.compensateBookedRelationship(userId, arrangementId);
    }


    @Override
    public User save(User user) {
        if (userRepository.existsById(user.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "User with id " + user.getId() + " already exists."
            );
        }
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User update(Long id, UserUpdateRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + id
                ));

        if (request.getUsername() != null) {
            existing.setUsername(request.getUsername());
        }

        if (request.getEmail() != null) {
            existing.setEmail(request.getEmail());
        }

        return userRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User with id " + id + " not found."
            );
        }

        userRepository.deleteById(id);
    }

    public User addOrUpdateViewed(Long userId, Long arrangementId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }

        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Arrangement not found with id: " + arrangementId);
        }

        return userRepository.addOrUpdateViewed(userId, arrangementId);
    }

    @Override
    public void deleteViewedRelationship(Long userId, Long arrangementId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        userRepository.deleteViewedRelationship(userId, arrangementId);
    }

    @Override
    public List<ViewedArrangementResponse> findUserWithViewedRelationships(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        return userRepository.findUserWithViewedRelationships(userId);
    }

    public User addOrUpdateBooked(Long userId, Long arrangementId, Integer persons, Double totalPrice) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id: " + userId);
        }

        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Arrangement not found with id: " + arrangementId);
        }

        return userRepository.addOrUpdateBooked(userId, arrangementId, persons, totalPrice);
    }

    @Override
    public void deleteBookedRelationship(Long userId, Long arrangementId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        userRepository.deleteBookedRelationship(userId, arrangementId);
    }

    @Override
    public  List<BookedArrangementResponse> findUserWithBookedRelationships(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        return userRepository.findUserWithBookedRelationships(userId);
    }

    @Override
    public User updateBookedRelationship(Long userId, Long arrangementId, Integer persons, Double totalPrice) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        User updated = userRepository.updateBookedRelationship(
                userId,
                arrangementId,
                persons,
                totalPrice
        );

        if (updated == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "BOOKED relationship not found between user " + userId +
                            " and arrangement " + arrangementId
            );
        }

        return updated;
    }

    @Override
    public List<ArrangementRecommendationDto> recommendBasedOnViewed(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        return userRepository.recommendBasedOnViewed(userId);
    }

    @Override
    public List<ArrangementRecommendationDto> recommendBasedOnBooked(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        return userRepository.recommendBasedOnBooked(userId);
    }
}