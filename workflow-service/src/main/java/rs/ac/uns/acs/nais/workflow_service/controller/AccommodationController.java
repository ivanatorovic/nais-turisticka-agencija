package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.AccommodationDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IAccommodationService;

import java.util.List;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    private final IAccommodationService accommodationService;

    public AccommodationController(IAccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @GetMapping
    public ResponseEntity<List<AccommodationDTO>> getAllAccommodations() {
        return ResponseEntity.ok(accommodationService.getAllAccommodations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDTO> getAccommodationById(@PathVariable Long id) {
        return ResponseEntity.ok(accommodationService.getAccommodationById(id));
    }

    @PostMapping
    public ResponseEntity<AccommodationDTO> createAccommodation(@RequestBody AccommodationDTO accommodationDTO) {
        AccommodationDTO createdAccommodation = accommodationService.createAccommodation(accommodationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccommodation);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AccommodationDTO> updateAccommodation(@PathVariable Long id,
                                                                @RequestBody AccommodationDTO accommodationDTO) {
        return ResponseEntity.ok(accommodationService.updateAccommodation(id, accommodationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteAccommodation(id);
        return ResponseEntity.noContent().build();
    }
}