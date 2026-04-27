package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.*;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;
import rs.ac.uns.acs.nais.workflow_service.repository.ArrangementRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.OfferRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.UserRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.WorkflowRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IArrangementService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArrangementService implements IArrangementService {

    private final ArrangementRepository arrangementRepository;
    private final WorkflowRepository workflowRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    public ArrangementService(ArrangementRepository arrangementRepository,
                              WorkflowRepository workflowRepository,
                              OfferRepository offerRepository,
                              UserRepository userRepository) {
        this.arrangementRepository = arrangementRepository;
        this.workflowRepository = workflowRepository;
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ArrangementDTO> getAllArrangements() {
        return arrangementRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HotelMealDTO> getAdminHotelsWithMealOptions(Long adminId) {

        if (!userRepository.existsById(adminId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Administrator not found with id: " + adminId
            );
        }

        return arrangementRepository.getAdminHotelsWithMealOptions(adminId);
    }

    @Override
    public ArrangementDTO getArrangementById(Long id) {
        Arrangement arrangement = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        return mapToDTO(arrangement);
    }

    @Override
    public ArrangementDTO createArrangement(ArrangementDTO arrangementDTO) {
        Long newId = arrangementRepository.findMaxId() + 1;

        Arrangement arrangement = new Arrangement();
        arrangement.setId(newId);
        arrangement.setName(arrangementDTO.getName());
        arrangement.setDescription(arrangementDTO.getDescription());
        arrangement.setDestination(arrangementDTO.getDestination());

        Arrangement savedArrangement = arrangementRepository.save(arrangement);

        return mapToDTO(savedArrangement);
    }

    @Override
    public ArrangementDTO updateArrangement(Long id, ArrangementDTO arrangementDTO) {
        Arrangement existingArrangement = arrangementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Arrangement not found with id: " + id
                ));

        if (arrangementDTO.getId() != null && !arrangementDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing arrangement ID is not allowed."
            );
        }

        if (arrangementDTO.getName() != null) {
            existingArrangement.setName(arrangementDTO.getName());
        }

        if (arrangementDTO.getDescription() != null) {
            existingArrangement.setDescription(arrangementDTO.getDescription());
        }

        if (arrangementDTO.getDestination() != null) {
            existingArrangement.setDestination(arrangementDTO.getDestination());
        }

        Arrangement updatedArrangement = arrangementRepository.save(existingArrangement);

        return mapToDTO(updatedArrangement);
    }

    @Override
    public void deleteArrangement(Long id) {
        if (!arrangementRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + id
            );
        }

        arrangementRepository.deleteById(id);
    }

    private ArrangementDTO mapToDTO(Arrangement arrangement) {
        return new ArrangementDTO(
                arrangement.getId(),
                arrangement.getName(),
                arrangement.getDescription(),
                arrangement.getDestination()
        );
    }

    @Override
    public ArrangementDTO setBasedOnWorkflow(Long arrangementId, Long workflowId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        if (!workflowRepository.existsById(workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Workflow not found with id: " + workflowId
            );
        }

        Arrangement arrangement = arrangementRepository.createOrUpdateBasedOnRelationship(
                arrangementId,
                workflowId
        );

        return mapToDTO(arrangement);
    }

    @Override
    public WorkflowDTO getWorkflowForArrangement(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        WorkflowDTO workflow = arrangementRepository.getWorkflowForArrangement(arrangementId);

        if (workflow == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement does not have BASED_ON workflow"
            );
        }

        return workflow;
    }


    @Override
    public void deleteBasedOnRelationship(Long arrangementId) {
        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found with id: " + arrangementId
            );
        }

        arrangementRepository.deleteBasedOnRelationship(arrangementId);
    }

    private WorkflowDTO mapWorkflowToDTO(Workflow workflow) {
        return new WorkflowDTO(
                workflow.getId(),
                workflow.getName()
        );
    }

    @Override
    public ArrangementDTO addOfferToArrangement(Long arrangementId, Long offerId) {

        Arrangement arrangement = arrangementRepository.findById(arrangementId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Arrangement not found"));

        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Offer not found"));

        if (arrangement.getOffers() == null) {
            arrangement.setOffers(new ArrayList<>());
        }

        arrangement.getOffers().add(offer);

        Arrangement saved = arrangementRepository.save(arrangement);

        return mapToDTO(saved);
    }

    @Override
    public List<OfferDTO> getOffersForArrangement(Long arrangementId) {

        if (!arrangementRepository.existsById(arrangementId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Arrangement not found"
            );
        }

        return arrangementRepository.getOffersForArrangement(arrangementId);
    }


    @Override
    public void removeOfferFromArrangement(Long arrangementId, Long offerId) {

        arrangementRepository.removeOfferFromArrangement(arrangementId, offerId);
    }

    @Override
    public List<SameTransportArrangementDTO> findArrangementsWithSameTransportAsAdminArrangements(Long adminId) {
        return arrangementRepository.findArrangementsWithSameTransportAsAdminArrangements(adminId);
    }

    @Override
    public List<OfferAverageRatingDTO> getOfferAverageRatings() {
        return arrangementRepository.getOfferAverageRatings();
    }

    @Override
    public List<AdminWorkflowArrangementCountDTO> getArrangementCountByAdminWorkflow() {
        return arrangementRepository.getArrangementCountByAdminWorkflow();
    }

    @Override
    public List<BetterAccommodationOfferDTO> findBetterAccommodationOffers(Long adminId) {
        return arrangementRepository.findBetterAccommodationOffers(adminId);
    }
}