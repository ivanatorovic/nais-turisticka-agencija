package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.AccommodationDTO;

import java.util.List;

public interface IAccommodationService {

    List<AccommodationDTO> getAllAccommodations();

    AccommodationDTO getAccommodationById(Long id);

    AccommodationDTO createAccommodation(AccommodationDTO accommodationDTO);

    AccommodationDTO updateAccommodation(Long id, AccommodationDTO accommodationDTO);

    void deleteAccommodation(Long id);
}