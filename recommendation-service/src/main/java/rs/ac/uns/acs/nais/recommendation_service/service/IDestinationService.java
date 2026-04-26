package rs.ac.uns.acs.nais.recommendation_service.service;

import rs.ac.uns.acs.nais.recommendation_service.dto.DestinationRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Destination;

import java.util.List;
import java.util.Optional;

public interface IDestinationService {
    Destination save(Destination destination);
    List<Destination> findAll();
    Optional<Destination> findById(Long id);
    Destination update(Long id, DestinationRequest destination);
    void delete(Long id);
}