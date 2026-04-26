package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.BookedArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.dto.UserUpdateRequest;
import rs.ac.uns.acs.nais.recommendation_service.dto.ViewedArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.model.User;
import rs.ac.uns.acs.nais.recommendation_service.repository.ArrangementRepository;
import rs.ac.uns.acs.nais.recommendation_service.repository.UserRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IUserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ArrangementRepository arrangementRepository;

    public UserService(UserRepository userRepository, ArrangementRepository arrangementRepository) {
        this.userRepository = userRepository;
        this.arrangementRepository = arrangementRepository;
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

    @Override
    public User addOrUpdateViewed(Long userId, Long arrangementId, String viewedAt) {
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

        return userRepository.addOrUpdateViewed(userId, arrangementId, viewedAt);
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

    @Override
    public User addOrUpdateBooked(Long userId, Long arrangementId, String bookingDate, Integer persons, Double totalPrice) {
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

        return userRepository.addOrUpdateBooked(userId, arrangementId, bookingDate, persons, totalPrice);
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