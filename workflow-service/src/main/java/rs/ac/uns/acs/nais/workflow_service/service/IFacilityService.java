package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.FacilityDTO;

import java.util.List;

public interface IFacilityService {

    List<FacilityDTO> getAllFacilities();

    FacilityDTO getFacilityById(Long id);

    FacilityDTO createFacility(FacilityDTO dto);

    FacilityDTO updateFacility(Long id, FacilityDTO dto);

    void deleteFacility(Long id);
}