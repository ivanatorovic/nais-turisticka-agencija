package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.AccommodationDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.TransportDTO;

import java.util.List;

public interface IOfferService {

    List<OfferDTO> getAllOffers();

    OfferDTO getOfferById(Long id);

    OfferDTO createOffer(OfferDTO offerDTO);

    OfferDTO updateOffer(Long id, OfferDTO offerDTO);

    void deleteOffer(Long id);

    OfferDTO setAccommodation(Long offerId, Long accommodationId);

    AccommodationDTO getAccommodation(Long offerId);

    void removeAccommodation(Long offerId);

    OfferDTO setTransport(Long offerId, Long transportId);

    TransportDTO getTransport(Long offerId);

    void removeTransport(Long offerId);
}