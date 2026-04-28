package rs.ac.uns.acs.nais.recommendation_service.service;

import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.BookedArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.dto.UserUpdateRequest;
import rs.ac.uns.acs.nais.recommendation_service.dto.ViewedArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(Long id);
    User update(Long id, UserUpdateRequest user);
    void delete(Long id);

    User addOrUpdateViewed(Long userId, Long arrangementId);
    void deleteViewedRelationship(Long userId, Long arrangementId);
    List<ViewedArrangementResponse> findUserWithViewedRelationships(Long userId);

    User addOrUpdateBooked(Long userId, Long arrangementId, Integer persons, Double totalPrice);
    void deleteBookedRelationship(Long userId, Long arrangementId);
    List<BookedArrangementResponse> findUserWithBookedRelationships(Long userId);
    User updateBookedRelationship(Long userId, Long arrangementId, Integer persons, Double totalPrice);

    List<ArrangementRecommendationDto> recommendBasedOnViewed(Long userId);
    List<ArrangementRecommendationDto> recommendBasedOnBooked(Long userId);
}