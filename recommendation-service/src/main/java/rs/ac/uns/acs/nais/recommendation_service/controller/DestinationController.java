package rs.ac.uns.acs.nais.recommendation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.recommendation_service.dto.DestinationRequest;
import rs.ac.uns.acs.nais.recommendation_service.model.Destination;
import rs.ac.uns.acs.nais.recommendation_service.service.impl.DestinationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @PostMapping
    public ResponseEntity<Destination> create(@RequestBody Destination destination) {
        return ResponseEntity.ok(destinationService.save(destination));
    }

    @GetMapping
    public ResponseEntity<List<Destination>> findAll() {
        return ResponseEntity.ok(destinationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Destination> findById(@PathVariable Long id) {
        Optional<Destination> destination = destinationService.findById(id);
        return destination.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Destination> update(@PathVariable Long id,
                                              @RequestBody DestinationRequest request) {
        return ResponseEntity.ok(destinationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        destinationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}