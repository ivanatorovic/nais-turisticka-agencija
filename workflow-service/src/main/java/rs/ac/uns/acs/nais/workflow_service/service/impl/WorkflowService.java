package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.CreateWorkflowRequest;
import rs.ac.uns.acs.nais.workflow_service.dto.CreatesDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Role;
import rs.ac.uns.acs.nais.workflow_service.model.User;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;
import rs.ac.uns.acs.nais.workflow_service.repository.UserRepository;
import rs.ac.uns.acs.nais.workflow_service.repository.WorkflowRepository;
import rs.ac.uns.acs.nais.workflow_service.service.IWorkflowService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkflowService implements IWorkflowService {

    private final WorkflowRepository workflowRepository;
    private final UserRepository userRepository;

    public WorkflowService(WorkflowRepository workflowRepository, UserRepository userRepository) {
        this.workflowRepository = workflowRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<WorkflowDTO> getAllWorkflows() {
        return workflowRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public WorkflowDTO getWorkflowById(Long id) {
        Workflow workflow = workflowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Workflow not found with id: " + id
                ));

        return mapToDTO(workflow);
    }

    @Override
    public WorkflowDTO createWorkflow(CreateWorkflowRequest request) {
        User creator = userRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + request.getCreatorId()
                ));

        if (creator.getRole() != Role.ADMINISTRATOR) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only administrator can create workflow."
            );
        }

        Long maxId = workflowRepository.findMaxId();
        Long newId = maxId + 1;

        Workflow workflow = new Workflow();
        workflow.setId(newId);
        workflow.setName(request.getName());

        Workflow savedWorkflow = workflowRepository.save(workflow);

        workflowRepository.createCreatesRelationship(
                creator.getId(),
                savedWorkflow.getId(),
                LocalDateTime.now()
        );

        return mapToDTO(savedWorkflow);
    }

    @Override
    public WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO) {
        Workflow existingWorkflow = workflowRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Workflow not found with id: " + id
                ));

        if (workflowDTO.getId() != null && !workflowDTO.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing workflow ID is not allowed."
            );
        }

        if (workflowDTO.getName() != null) {
            existingWorkflow.setName(workflowDTO.getName());
        }

        Workflow updatedWorkflow = workflowRepository.save(existingWorkflow);
        return mapToDTO(updatedWorkflow);
    }

    @Override
    public void deleteWorkflow(Long id) {
        if (!workflowRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Workflow not found with id: " + id
            );
        }

        workflowRepository.deleteWorkflowByCustomId(id);
    }

    @Override
    public CreatesDTO createCreatesRelationship(Long userId, Long workflowId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (workflowRepository.workflowAlreadyHasCreator(workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Workflow already has an administrator."
            );
        }

        if (user.getRole() != Role.ADMINISTRATOR) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Only administrator can create CREATES relationship."
            );
        }

        if (!workflowRepository.existsById(workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Workflow not found with id: " + workflowId
            );
        }

        if (workflowRepository.existsCreatesRelationship(userId, workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CREATES relationship already exists."
            );
        }

        LocalDateTime createdAt = LocalDateTime.now();

        workflowRepository.createCreatesRelationship(userId, workflowId, createdAt);

        return new CreatesDTO(userId, workflowId, createdAt);
    }

    @Override
    public List<CreatesDTO> getAllCreatesRelationships() {
        return workflowRepository.getAllCreatesRelationships();
    }

    @Override
    public CreatesDTO getCreatesRelationship(Long userId, Long workflowId) {
        CreatesDTO relationship =
                workflowRepository.getCreatesRelationship(userId, workflowId);

        if (relationship == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CREATES relationship not found."
            );
        }

        return relationship;
    }

    @Override
    public CreatesDTO updateCreatesRelationship(Long userId, Long workflowId, LocalDateTime createdAt) {
        if (!workflowRepository.existsCreatesRelationship(userId, workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CREATES relationship not found."
            );
        }

        workflowRepository.updateCreatesRelationship(userId, workflowId, createdAt);

        return new CreatesDTO(userId, workflowId, createdAt);
    }

    @Override
    public void deleteCreatesRelationship(Long userId, Long workflowId) {
        if (!workflowRepository.existsCreatesRelationship(userId, workflowId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "CREATES relationship not found."
            );
        }

        workflowRepository.deleteCreatesRelationship(userId, workflowId);
    }

    @Override
    public List<WorkflowDTO> getWorkflowsCreatedByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        return workflowRepository.getWorkflowsCreatedByUser(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private WorkflowDTO mapToDTO(Workflow workflow) {
        WorkflowDTO dto = new WorkflowDTO();
        dto.setId(workflow.getId());
        dto.setName(workflow.getName());
        return dto;
    }
}