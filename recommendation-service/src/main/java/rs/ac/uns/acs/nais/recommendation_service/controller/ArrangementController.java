package rs.ac.uns.acs.nais.recommendation_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRequest;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.dto.UpdateArrangementRequest;
import rs.ac.uns.acs.nais.recommendation_service.mapper.ArrangementMapper;
import rs.ac.uns.acs.nais.recommendation_service.model.Arrangement;
import rs.ac.uns.acs.nais.recommendation_service.service.impl.ArrangementService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/arrangements")
public class ArrangementController {

    private final ArrangementService arrangementService;

    public ArrangementController(ArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }

    @PostMapping
    public ResponseEntity<ArrangementResponse> create(@RequestBody ArrangementRequest request) {
        Arrangement arrangement = ArrangementMapper.toEntity(request);
        Arrangement saved = arrangementService.save(arrangement);
        return ResponseEntity.ok(ArrangementMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<ArrangementResponse>> findAll() {
        List<ArrangementResponse> arrangements = arrangementService.findAll()
                .stream()
                .map(ArrangementMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(arrangements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArrangementResponse> findById(@PathVariable Long id) {
        Optional<Arrangement> arrangement = arrangementService.findById(id);
        return arrangement.map(a -> ResponseEntity.ok(ArrangementMapper.toResponse(a)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArrangementResponse> update(@PathVariable Long id,
                                                      @RequestBody UpdateArrangementRequest request) {

        Arrangement updated = arrangementService.update(id, request);
        return ResponseEntity.ok(ArrangementMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        arrangementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{arrangementId}/tags/{tagId}")
    public ResponseEntity<Void> addTagToArrangement(@PathVariable Long arrangementId,
                                                    @PathVariable Long tagId) {
        arrangementService.addTagToArrangement(arrangementId, tagId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{arrangementId}/tags/{tagId}")
    public ResponseEntity<Void> removeTagFromArrangement(@PathVariable Long arrangementId,
                                                         @PathVariable Long tagId) {
        arrangementService.removeTagFromArrangement(arrangementId, tagId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{arrangementId}/tags")
    public ResponseEntity<Arrangement> getArrangementWithTags(@PathVariable Long arrangementId) {
        return ResponseEntity.ok(arrangementService.getArrangementWithTags(arrangementId));
    }

    @PostMapping("/{arrangementId}/destination/{destinationId}")
    public ResponseEntity<Void> setArrangementLocation(@PathVariable Long arrangementId,
                                                       @PathVariable Long destinationId) {
        arrangementService.setArrangementLocation(arrangementId, destinationId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{arrangementId}/destination")
    public ResponseEntity<Void> removeArrangementLocation(@PathVariable Long arrangementId) {
        arrangementService.removeArrangementLocation(arrangementId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{arrangementId}/destination")
    public ResponseEntity<Arrangement> getArrangementWithDestination(@PathVariable Long arrangementId) {
        return ResponseEntity.ok(arrangementService.getArrangementWithDestination(arrangementId));
    }

    @GetMapping("/{arrangementId}/similar")
    public ResponseEntity<List<ArrangementRecommendationDto>> findSimilarArrangements(@PathVariable Long arrangementId) {
        return ResponseEntity.ok(arrangementService.findSimilarArrangements(arrangementId));
    }
}