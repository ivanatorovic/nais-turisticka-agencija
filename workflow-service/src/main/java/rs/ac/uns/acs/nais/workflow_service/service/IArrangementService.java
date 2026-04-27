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

    void deleteBasedOnRelationship(Long arrangementId);

    ArrangementDTO addOfferToArrangement(Long arrangementId, Long offerId);

    List<OfferDTO> getOffersForArrangement(Long arrangementId);

    void removeOfferFromArrangement(Long arrangementId, Long offerId);

    List<SameTransportArrangementDTO> findArrangementsWithSameTransportAsAdminArrangements(Long adminId);

    List<OfferAverageRatingDTO> getOfferAverageRatings();

    List<AdminWorkflowArrangementCountDTO> getArrangementCountByAdminWorkflow();

    List<BetterAccommodationOfferDTO> findBetterAccommodationOffers(Long adminId);

    List<HotelMealDTO> getAdminHotelsWithMealOptions(Long adminId);


}