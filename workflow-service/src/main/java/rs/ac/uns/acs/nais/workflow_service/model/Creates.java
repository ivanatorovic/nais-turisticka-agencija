package rs.ac.uns.acs.nais.workflow_service.model;

import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;


@RelationshipProperties
public class Creates {

    @RelationshipId
    private Long id;

    private LocalDate createdAt;

    @TargetNode
    private Workflow workflow;

    public Creates() {
    }

    public Creates(LocalDate createdAt, Workflow workflow) {
        this.createdAt = createdAt;
        this.workflow = workflow;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setWorkflow(Workflow workflow) {
        this.workflow = workflow;
    }
}