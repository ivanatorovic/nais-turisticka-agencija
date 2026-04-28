package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.CreateWorkflowRequest;
import rs.ac.uns.acs.nais.workflow_service.dto.CreatesDTO;
import rs.ac.uns.acs.nais.workflow_service.dto.WorkflowDTO;
import rs.ac.uns.acs.nais.workflow_service.service.IWorkflowService;


import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowController {

    private final IWorkflowService workflowService;

    public WorkflowController(IWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping
    public List<WorkflowDTO> getAllWorkflows() {
        return workflowService.getAllWorkflows();
    }

    @GetMapping("/{id}")
    public WorkflowDTO getWorkflowById(@PathVariable Long id) {
        return workflowService.getWorkflowById(id);
    }

    @PostMapping
    public WorkflowDTO createWorkflow(@RequestBody CreateWorkflowRequest request) {
        return workflowService.createWorkflow(request);
    }

    @PatchMapping("/{id}")
    public WorkflowDTO updateWorkflow(@PathVariable Long id,
                                      @RequestBody WorkflowDTO workflowDTO) {
        return workflowService.updateWorkflow(id, workflowDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteWorkflow(@PathVariable Long id) {
        workflowService.deleteWorkflow(id);
    }

    @PostMapping("/{workflowId}/created-by/{userId}")
    public CreatesDTO createCreatesRelationship(@PathVariable Long workflowId,
                                                            @PathVariable Long userId) {
        return workflowService.createCreatesRelationship(userId, workflowId);
    }

    @GetMapping("/creates")
    public List<CreatesDTO> getAllCreatesRelationships() {
        return workflowService.getAllCreatesRelationships();
    }

    @GetMapping("/{workflowId}/created-by/{userId}")
    public CreatesDTO getCreatesRelationship(@PathVariable Long workflowId,
                                                         @PathVariable Long userId) {
        return workflowService.getCreatesRelationship(userId, workflowId);
    }

    @PatchMapping("/{workflowId}/created-by/{userId}")
    public CreatesDTO updateCreatesRelationship(@PathVariable Long workflowId,
                                                            @PathVariable Long userId,
                                                            @RequestBody CreatesDTO dto) {
        return workflowService.updateCreatesRelationship(
                userId,
                workflowId,
                dto.getCreatedAt()
        );
    }

    @DeleteMapping("/{workflowId}/created-by/{userId}")
    public void deleteCreatesRelationship(@PathVariable Long workflowId,
                                          @PathVariable Long userId) {
        workflowService.deleteCreatesRelationship(userId, workflowId);
    }

    @GetMapping("/created-by/{userId}")
    public List<WorkflowDTO> getWorkflowsCreatedByUser(@PathVariable Long userId) {
        return workflowService.getWorkflowsCreatedByUser(userId);
    }
}