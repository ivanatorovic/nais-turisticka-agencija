package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.AccommodationDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.OfferDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.TransportDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Accommodation;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.model.Transport;
import rs.ac.uns.acs.nais.workflow_service.repository.AccommodationRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.OfferRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.TransportRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IOfferService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService implements IOfferService {

    private final OfferRepository offerRepository;
    private final AccommodationRepository accommodationRepository;
    private final TransportRepository transportRepository;

    public OfferService(OfferRepository offerRepository,
                        AccommodationRepository accommodationRepository,
                        TransportRepository transportRepository) {
        this.offerRepository = offerRepository;
        this.accommodationRepository = accommodationRepository;
        this.transportRepository = transportRepository;
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

    public OfferDTO setAccommodation(Long offerId, Long accommodationId) {

        if (!offerRepository.existsById(offerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        }

        if (!accommodationRepository.existsById(accommodationId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found");
        }

        Offer o = offerRepository.setAccommodationForOffer(offerId, accommodationId);

        return mapToDTO(o);
    }

    public AccommodationDTO getAccommodation(Long offerId) {

        Accommodation a = offerRepository.getAccommodationForOffer(offerId);

        if (a == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No accommodation for this offer");
        }

        return new AccommodationDTO(
                a.getId(),
                a.getName(),
                a.getType(),
                a.getRating()
        );
    }

    public List<OfferDTO> getOffersByAccommodation(Long accommodationId) {

        return offerRepository.getOffersForAccommodation(accommodationId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void removeAccommodation(Long offerId) {

        if (!offerRepository.existsById(offerId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        }

        offerRepository.removeAccommodationFromOffer(offerId);
    }

    @Override
    public OfferDTO setTransport(Long offerId, Long transportId) {
        if (!offerRepository.existsById(offerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Offer not found with id: " + offerId
            );
        }

        if (!transportRepository.existsById(transportId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transport not found with id: " + transportId
            );
        }

        Offer offer = offerRepository.setTransportForOffer(offerId, transportId);

        return mapToDTO(offer);
    }

    @Override
    public TransportDTO getTransport(Long offerId) {
        if (!offerRepository.existsById(offerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Offer not found with id: " + offerId
            );
        }

        Transport transport = offerRepository.getTransportForOffer(offerId);

        if (transport == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Offer does not have transport"
            );
        }

        return mapTransportToDTO(transport);
    }

    @Override
    public List<OfferDTO> getOffersByTransport(Long transportId) {
        if (!transportRepository.existsById(transportId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transport not found with id: " + transportId
            );
        }

        return offerRepository.getOffersForTransport(transportId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void removeTransport(Long offerId) {
        if (!offerRepository.existsById(offerId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Offer not found with id: " + offerId
            );
        }

        offerRepository.removeTransportFromOffer(offerId);
    }

    private TransportDTO mapTransportToDTO(Transport transport) {
        return new TransportDTO(
                transport.getId(),
                transport.getType(),
                transport.getCompany(),
                transport.getRating()
        );
    }
}