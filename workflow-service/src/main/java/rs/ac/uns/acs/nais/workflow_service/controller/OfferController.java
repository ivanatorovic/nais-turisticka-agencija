package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IOfferService;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final IOfferService offerService;

    public OfferController(IOfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public ResponseEntity<List<OfferDTO>> getAllOffers() {
        return ResponseEntity.ok(offerService.getAllOffers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferDTO> getOfferById(@PathVariable Long id) {
        return ResponseEntity.ok(offerService.getOfferById(id));
    }

    @PostMapping
    public ResponseEntity<OfferDTO> createOffer(@RequestBody OfferDTO offerDTO) {
        OfferDTO createdOffer = offerService.createOffer(offerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOffer);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OfferDTO> updateOffer(@PathVariable Long id,
                                                @RequestBody OfferDTO offerDTO) {
        return ResponseEntity.ok(offerService.updateOffer(id, offerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return ResponseEntity.noContent().build();
    }
}