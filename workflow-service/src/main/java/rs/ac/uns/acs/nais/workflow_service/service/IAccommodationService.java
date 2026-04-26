package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.AccommodationDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.FacilityDTO;

import java.util.List;

public interface IAccommodationService {

    List<AccommodationDTO> getAllAccommodations();

    AccommodationDTO getAccommodationById(Long id);

    AccommodationDTO createAccommodation(AccommodationDTO accommodationDTO);

    AccommodationDTO updateAccommodation(Long id, AccommodationDTO accommodationDTO);

    void deleteAccommodation(Long id);

    AccommodationDTO addFacilityToHotel(Long accommodationId, Long facilityId);

    List<FacilityDTO> getFacilitiesForAccommodation(Long accommodationId);

    List<AccommodationDTO> getAccommodationsByFacility(Long facilityId);

    void removeFacilityFromAccommodation(Long accommodationId, Long facilityId);
}