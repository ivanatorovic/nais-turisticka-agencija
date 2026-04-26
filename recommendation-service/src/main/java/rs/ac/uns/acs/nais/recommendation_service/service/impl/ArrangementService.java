package rs.ac.uns.acs.nais.recommendation_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.UpdateArrangementRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Arrangement;
import rs.ac.uns.acs.nais.recommendation_service.repository.ArrangementRepository;
import rs.ac.uns.acs.nais.recommendation_service.repository.DestinationRepository;
import rs.ac.uns.acs.nais.recommendation_service.repository.TagRepository;
import rs.ac.uns.acs.nais.recommendation_service.service.IArrangementService;

import javax.print.attribute.standard.Destination;
import java.util.List;
import java.util.Optional;

@Service
public class ArrangementService implements IArrangementService {

    private final ArrangementRepository arrangementRepository;
    private final TagRepository tagRepository;
    private final DestinationRepository destinationRepository;

    public ArrangementService(ArrangementRepository arrangementRepository, TagRepository tagRepository, DestinationRepository destinationRepository) {
        this.arrangementRepository = arrangementRepository;
        this.tagRepository = tagRepository;
        this.destinationRepository = destinationRepository;
    }


    @Override
    public Arrangement save(Arrangement arrangement) {
        if (arrangementRepository.existsById(arrangement.getId())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Arrangement with id " + arrangement.getId() + " already exists."
            );
        }
        return arrangementRepository.save(arrangement);
    }

    // ===================== READ =====================
    @Override
    public List<Arrangement> findAll() {
        return arrangementRepository.findAll();
    }

    @Override
    public Optional<Arrangement> findById(Long id) {
        return arrangementRepository.findById(id);
    }

    @Override
    public Arrangement update(Long id, UpdateArrangementRequest request) {
        Arrangement existing = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        if (request.getName() != null) {
            existing.setName(request.getName());
        }

        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }

        if (request.getPrice() != null) {
            existing.setPrice(request.getPrice());
        }

        if (request.getDurationDays() != null) {
            existing.setDurationDays(request.getDurationDays());
        }

        return arrangementRepository.save(existing);
    }


    @Override
    public void delete(Long id) {
        if (!arrangementRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + id
            );
        }

        arrangementRepository.deleteArrangementById(id);
    }


    @Override
    public Arrangement addTagToArrangement(Long arrangementId, Long tagId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        if (!tagRepository.existsById(tagId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Tag not found with id: " + tagId
            );
        }

        return arrangementRepository.addTagToArrangement(arrangementId, tagId);
    }

    @Override
    public void removeTagFromArrangement(Long arrangementId, Long tagId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }
        if (!tagRepository.existsById(tagId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Tag not found with id: " + tagId
            );
        }

        arrangementRepository.removeTagFromArrangement(arrangementId, tagId);
    }

    @Override
    public Arrangement getArrangementWithTags(Long arrangementId) {
        Arrangement result = arrangementRepository.getArrangementWithTags(arrangementId);

        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return result;
    }


    @Override
    public Arrangement setArrangementLocation(Long arrangementId, Long destinationId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }
        if (!destinationRepository.existsById(destinationId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "destination not found with id: " + destinationId
            );
        }

        return arrangementRepository.setArrangementLocation(arrangementId, destinationId);
    }

    @Override
    public void removeArrangementLocation(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        arrangementRepository.removeArrangementLocation(arrangementId);
    }

    @Override
    public Arrangement getArrangementWithDestination(Long arrangementId) {
        Arrangement result = arrangementRepository.getArrangementWithDestination(arrangementId);

        if (result == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return result;
    }


    @Override
    public List<ArrangementRecommendationDto> findSimilarArrangements(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        return arrangementRepository.findSimilarArrangements(arrangementId);
    }
}