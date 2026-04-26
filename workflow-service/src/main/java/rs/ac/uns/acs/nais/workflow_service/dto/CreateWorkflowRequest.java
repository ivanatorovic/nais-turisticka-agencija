package rs.ac.uns.acs.nais.workflow_service.dto;

public class CreateWorkflowRequest {

    private String name;
    private Long creatorId;

    public CreateWorkflowRequest() {
    }

    public String getName() {
        return name;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }
}