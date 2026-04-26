package rs.ac.uns.acs.nais.recommendation_service.service;

import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.UpdateArrangementRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Arrangement;

import java.util.List;
import java.util.Optional;

public interface IArrangementService {
    Arrangement save(Arrangement arrangement);
    List<Arrangement> findAll();
    Optional<Arrangement> findById(Long id);
    Arrangement update(Long id, UpdateArrangementRequest arrangement);
    void delete(Long id);

    Arrangement addTagToArrangement(Long arrangementId, Long tagId);
    void removeTagFromArrangement(Long arrangementId, Long tagId);
    Arrangement getArrangementWithTags(Long arrangementId);

    Arrangement setArrangementLocation(Long arrangementId, Long destinationId);
    void removeArrangementLocation(Long arrangementId);
    Arrangement getArrangementWithDestination(Long arrangementId);


    List<ArrangementRecommendationDto> findSimilarArrangements(Long arrangementId);
}