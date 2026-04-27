package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.*;
import rs.ac.uns.acs.nais.workflow_service.service.IArrangementService;

import java.util.List;

@RestController
@RequestMapping("/api/arrangements")
public class ArrangementController {

    private final IArrangementService arrangementService;

    public ArrangementController(IArrangementService arrangementService) {
        this.arrangementService = arrangementService;
    }

    @GetMapping
    public ResponseEntity<List<ArrangementDTO>> getAllArrangements() {
        return ResponseEntity.ok(arrangementService.getAllArrangements());
    }

    @GetMapping("/admin/{adminId}/hotels/meal-options")
    public ResponseEntity<List<HotelMealDTO>> getAdminHotelsWithMealOptions(
            @PathVariable Long adminId) {

        return ResponseEntity.ok(
                arrangementService.getAdminHotelsWithMealOptions(adminId)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArrangementDTO> getArrangementById(@PathVariable Long id) {
        return ResponseEntity.ok(arrangementService.getArrangementById(id));
    }

    @PostMapping
    public ResponseEntity<ArrangementDTO> createArrangement(@RequestBody ArrangementDTO arrangementDTO) {
        ArrangementDTO createdArrangement = arrangementService.createArrangement(arrangementDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArrangement);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ArrangementDTO> updateArrangement(@PathVariable Long id,
                                                            @RequestBody ArrangementDTO arrangementDTO) {
        return ResponseEntity.ok(arrangementService.updateArrangement(id, arrangementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArrangement(@PathVariable Long id) {
        arrangementService.deleteArrangement(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{arrangementId}/based-on/{workflowId}")
    public ResponseEntity<ArrangementDTO> setBasedOnWorkflow(@PathVariable Long arrangementId,
                                                             @PathVariable Long workflowId) {
        return ResponseEntity.ok(arrangementService.setBasedOnWorkflow(arrangementId, workflowId));
    }

    @GetMapping("/{arrangementId}/workflow")
    public ResponseEntity<WorkflowDTO> getWorkflowForArrangement(@PathVariable Long arrangementId) {
        return ResponseEntity.ok(arrangementService.getWorkflowForArrangement(arrangementId));
    }

    @GetMapping("/workflow/{workflowId}")
    public ResponseEntity<List<ArrangementDTO>> getArrangementsByWorkflow(@PathVariable Long workflowId) {
        return ResponseEntity.ok(arrangementService.getArrangementsByWorkflow(workflowId));
    }

    @DeleteMapping("/{arrangementId}/based-on")
    public ResponseEntity<Void> deleteBasedOnRelationship(@PathVariable Long arrangementId) {
        arrangementService.deleteBasedOnRelationship(arrangementId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{arrangementId}/offers/{offerId}")
    public ResponseEntity<ArrangementDTO> addOffer(@PathVariable Long arrangementId,
                                                   @PathVariable Long offerId) {
        return ResponseEntity.ok(arrangementService.addOfferToArrangement(arrangementId, offerId));
    }

    @GetMapping("/{arrangementId}/offers")
    public ResponseEntity<List<OfferDTO>> getOffers(@PathVariable Long arrangementId) {
        return ResponseEntity.ok(arrangementService.getOffersForArrangement(arrangementId));
    }

    @GetMapping("/offers/{offerId}/arrangement")
    public ResponseEntity<ArrangementDTO> getArrangement(@PathVariable Long offerId) {
        return ResponseEntity.ok(arrangementService.getArrangementForOffer(offerId));
    }

    @DeleteMapping("/{arrangementId}/offers/{offerId}")
    public ResponseEntity<Void> removeOffer(@PathVariable Long arrangementId,
                                            @PathVariable Long offerId) {
        arrangementService.removeOfferFromArrangement(arrangementId, offerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/same-transport/admin/{adminId}")
    public List<SameTransportArrangementDTO> findArrangementsWithSameTransportAsAdminArrangements(
            @PathVariable Long adminId
    ) {
        return arrangementService.findArrangementsWithSameTransportAsAdminArrangements(adminId);
    }

    @GetMapping("/offer-average-ratings")
    public List<OfferAverageRatingDTO> getOfferAverageRatings() {
        return arrangementService.getOfferAverageRatings();
    }

    @GetMapping("/admin-workflow-arrangement-count")
    public List<AdminWorkflowArrangementCountDTO> getArrangementCountByAdminWorkflow() {
        return arrangementService.getArrangementCountByAdminWorkflow();
    }

    @GetMapping("/better-accommodation-offers/admin/{adminId}")
    public List<BetterAccommodationOfferDTO> findBetterAccommodationOffers(@PathVariable Long adminId) {
        return arrangementService.findBetterAccommodationOffers(adminId);
    }


}