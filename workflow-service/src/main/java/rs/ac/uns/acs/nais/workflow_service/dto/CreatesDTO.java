package rs.ac.uns.acs.nais.workflow_service.dto;

import java.time.LocalDateTime;

public class CreatesDTO {

    private Long userId;
    private Long workflowId;
    private LocalDateTime createdAt;

    public CreatesDTO() {
    }

    public CreatesDTO(Long userId, Long workflowId, LocalDateTime createdAt) {
        this.userId = userId;
        this.workflowId = workflowId;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}