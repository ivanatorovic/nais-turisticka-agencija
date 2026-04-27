package rs.ac.uns.acs.nais.workflow_service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreatesDTO {

    private Long userId;
    private Long workflowId;
    private LocalDate createdAt;

    public CreatesDTO() {
    }

    public CreatesDTO(Long userId, Long workflowId, LocalDate createdAt) {
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}