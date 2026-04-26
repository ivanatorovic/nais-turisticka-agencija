package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.AccommodationDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Accommodation;
import rs.ac.uns.acs.nais.workflow_service.repository.AccommodationRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IAccommodationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccommodationService implements IAccommodationService {

    private final AccommodationRepository accommodationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<AccommodationDTO> getAllAccommodations() {
        return accommodationRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccommodationDTO getAccommodationById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Accommodation not found with id: " + id
                ));

        return mapToDTO(accommodation);
    }

    @Override
    public AccommodationDTO createAccommodation(AccommodationDTO accommodationDTO) {
        validateRating(accommodationDTO.getRating());

        Long newId = accommodationRepository.findMaxId() + 1;

        Accommodation accommodation = new Accommodation();
        accommodation.setId(newId);
        accommodation.setName(accommodationDTO.getName());
        accommodation.setType(accommodationDTO.getType());
        accommodation.setRating(accommodationDTO.getRating());

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        return mapToDTO(savedAccommodation);
    }

    @Override
    public AccommodationDTO updateAccommodation(Long id, AccommodationDTO accommodationDTO) {
        Accommodation existingAccommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Accommodation not found with id: " + id
                ));

        if (accommodationDTO.getId() != null && !accommodationDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing accommodation ID is not allowed."
            );
        }

        validateRating(accommodationDTO.getRating());

        if (accommodationDTO.getName() != null) {
            existingAccommodation.setName(accommodationDTO.getName());
        }

        if (accommodationDTO.getType() != null) {
            existingAccommodation.setType(accommodationDTO.getType());
        }

        if (accommodationDTO.getRating() != null) {
            existingAccommodation.setRating(accommodationDTO.getRating());
        }

        Accommodation updatedAccommodation = accommodationRepository.save(existingAccommodation);

        return mapToDTO(updatedAccommodation);
    }

    @Override
    public void deleteAccommodation(Long id) {
        if (!accommodationRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Accommodation not found with id: " + id
            );
        }

        accommodationRepository.deleteById(id);
    }

    private void validateRating(Double rating) {
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Rating must be between 1 and 5."
            );
        }
    }

    private AccommodationDTO mapToDTO(Accommodation accommodation) {
        return new AccommodationDTO(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getType(),
                accommodation.getRating()
        );
    }
}