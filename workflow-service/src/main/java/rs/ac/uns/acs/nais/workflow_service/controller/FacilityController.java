package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.FacilityDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IFacilityService;

import java.util.List;

@RestController
@RequestMapping("/api/facilities")
public class FacilityController {

    private final IFacilityService facilityService;

    public FacilityController(IFacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getAll() {
        return ResponseEntity.ok(facilityService.getAllFacilities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacilityDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facilityService.getFacilityById(id));
    }

    @PostMapping
    public ResponseEntity<FacilityDTO> create(@RequestBody FacilityDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(facilityService.createFacility(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FacilityDTO> update(@PathVariable Long id,
                                              @RequestBody FacilityDTO dto) {
        return ResponseEntity.ok(facilityService.updateFacility(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ResponseEntity.noContent().build();
    }
}