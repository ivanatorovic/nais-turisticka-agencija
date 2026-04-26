package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.FacilityDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Facility;
import rs.ac.uns.acs.nais.workflow_service.repository.FacilityRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IFacilityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityService implements IFacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityService(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<FacilityDTO> getAllFacilities() {
        return facilityRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FacilityDTO getFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Facility not found with id: " + id
                ));

        return mapToDTO(facility);
    }

    @Override
    public FacilityDTO createFacility(FacilityDTO dto) {

        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Facility name is required"
            );
        }

        Long newId = facilityRepository.findMaxId() + 1;

        Facility f = new Facility();
        f.setId(newId);
        f.setName(dto.getName());

        return mapToDTO(facilityRepository.save(f));
    }

    @Override
    public FacilityDTO updateFacility(Long id, FacilityDTO dto) {

        Facility existing = facilityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Facility not found"
                ));

        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing ID is not allowed"
            );
        }

        if (dto.getName() != null) {
            existing.setName(dto.getName());
        }

        return mapToDTO(facilityRepository.save(existing));
    }

    @Override
    public void deleteFacility(Long id) {

        if (!facilityRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Facility not found"
            );
        }

        facilityRepository.deleteById(id);
    }

    private FacilityDTO mapToDTO(Facility f) {
        return new FacilityDTO(f.getId(), f.getName());
    }
}