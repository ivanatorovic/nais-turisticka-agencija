package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.recommendation_service.dto.DestinationRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Destination;
import rs.ac.uns.acs.nais.recommendation_service.repository.DestinationRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IDestinationService;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationService implements IDestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public Destination save(Destination destination) {
        if (destinationRepository.existsById(destination.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Destination with id " + destination.getId() + " already exists."
            );
        }

        return destinationRepository.save(destination);
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Optional<Destination> findById(Long id) {
        return destinationRepository.findById(id);
    }

    @Override
    public Destination update(Long id, DestinationRequest request) {
        Destination existing = destinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Destination not found with id: " + id
                ));

        if (request.getName() != null) {
            existing.setName(request.getName());
        }

        if (request.getCountry() != null) {
            existing.setCountry(request.getCountry());
        }

        return destinationRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!destinationRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Destination not found with id: " + id
            );
        }

        destinationRepository.deleteById(id);
    }
}