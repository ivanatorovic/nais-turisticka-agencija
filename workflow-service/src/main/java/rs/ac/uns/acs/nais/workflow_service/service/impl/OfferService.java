package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.repository.OfferRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IOfferService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public List<OfferDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OfferDTO getOfferById(Long id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Offer not found with id: " + id
                ));

        return mapToDTO(offer);
    }

    @Override
    public OfferDTO createOffer(OfferDTO offerDTO) {
        Long newId = offerRepository.findMaxId() + 1;

        Offer offer = new Offer();
        offer.setId(newId);
        offer.setStartDate(offerDTO.getStartDate());
        offer.setEndDate(offerDTO.getEndDate());
        offer.setPriceForChildren(offerDTO.getPriceForChildren());
        offer.setPriceForAdults(offerDTO.getPriceForAdults());

        Offer savedOffer = offerRepository.save(offer);

        return mapToDTO(savedOffer);
    }

    @Override
    public OfferDTO updateOffer(Long id, OfferDTO offerDTO) {
        Offer existingOffer = offerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Offer not found with id: " + id
                ));

        if (offerDTO.getId() != null && !offerDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing offer ID is not allowed."
            );
        }

        if (offerDTO.getStartDate() != null) {
            existingOffer.setStartDate(offerDTO.getStartDate());
        }

        if (offerDTO.getEndDate() != null) {
            existingOffer.setEndDate(offerDTO.getEndDate());
        }

        if (offerDTO.getPriceForChildren() != null) {
            existingOffer.setPriceForChildren(offerDTO.getPriceForChildren());
        }

        if (offerDTO.getPriceForAdults() != null) {
            existingOffer.setPriceForAdults(offerDTO.getPriceForAdults());
        }

        Offer updatedOffer = offerRepository.save(existingOffer);

        return mapToDTO(updatedOffer);
    }

    @Override
    public void deleteOffer(Long id) {
        if (!offerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Offer not found with id: " + id
            );
        }

        offerRepository.deleteById(id);
    }

    private OfferDTO mapToDTO(Offer offer) {
        return new OfferDTO(
                offer.getId(),
                offer.getStartDate(),
                offer.getEndDate(),
                offer.getPriceForChildren(),
                offer.getPriceForAdults()
        );
    }
}