package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.*;

import java.util.List;

public interface IArrangementService {

    List<ArrangementDTO> getAllArrangements();

    ArrangementDTO getArrangementById(Long id);

    ArrangementDTO createArrangement(ArrangementDTO arrangementDTO);

    ArrangementDTO updateArrangement(Long id, ArrangementDTO arrangementDTO);

    void deleteArrangement(Long id);

    ArrangementDTO setBasedOnWorkflow(Long arrangementId, Long workflowId);

    WorkflowDTO getWorkflowForArrangement(Long arrangementId);

    List<ArrangementDTO> getArrangementsByWorkflow(Long workflowId);

    void deleteBasedOnRelationship(Long arrangementId);

    ArrangementDTO addOfferToArrangement(Long arrangementId, Long offerId);

    List<OfferDTO> getOffersForArrangement(Long arrangementId);

    ArrangementDTO getArrangementForOffer(Long offerId);

    void removeOfferFromArrangement(Long arrangementId, Long offerId);

    List<SameTransportArrangementDTO> findArrangementsWithSameTransportAsAdminArrangements(Long adminId);

    List<OfferAverageRatingDTO> getOfferAverageRatings();

    List<AdminWorkflowArrangementCountDTO> getArrangementCountByAdminWorkflow();

    List<HotelFacilitiesDTO> getHotelsWithFacilities();

    List<BetterAccommodationOfferDTO> findBetterAccommodationOffers(Long adminId);
}