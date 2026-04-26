package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.CreateWorkflowRequest;
import rs.ac.uns.acs.nais.workflow_service.dto.CreatesDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface IWorkflowService {

    List<WorkflowDTO> getAllWorkflows();

    WorkflowDTO getWorkflowById(Long id);

    WorkflowDTO createWorkflow(CreateWorkflowRequest request);

    WorkflowDTO updateWorkflow(Long id, WorkflowDTO workflowDTO);

    void deleteWorkflow(Long id);

    CreatesDTO createCreatesRelationship(Long userId, Long workflowId);

    List<CreatesDTO> getAllCreatesRelationships();

    CreatesDTO getCreatesRelationship(Long userId, Long workflowId);

    CreatesDTO updateCreatesRelationship(Long userId, Long workflowId, LocalDateTime createdAt);

    void deleteCreatesRelationship(Long userId, Long workflowId);

    List<WorkflowDTO> getWorkflowsCreatedByUser(Long userId);
}