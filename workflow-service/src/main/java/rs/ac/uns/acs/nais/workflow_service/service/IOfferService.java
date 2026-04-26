package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;

import java.util.List;

public interface IOfferService {

    List<OfferDTO> getAllOffers();

    OfferDTO getOfferById(Long id);

    OfferDTO createOffer(OfferDTO offerDTO);

    OfferDTO updateOffer(Long id, OfferDTO offerDTO);

    void deleteOffer(Long id);
}