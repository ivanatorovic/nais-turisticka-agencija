package rs.ac.uns.acs.nais.workflow_service.dto;

public class AdminWorkflowArrangementCountDTO {

    private Long adminId;
    private String administrator;
    private Long workflowId;
    private String workflowName;
    private Long numberOfArrangements;

    public AdminWorkflowArrangementCountDTO() {}

    public AdminWorkflowArrangementCountDTO(Long adminId, String administrator, Long workflowId,
                                            String workflowName, Long numberOfArrangements) {
        this.adminId = adminId;
        this.administrator = administrator;
        this.workflowId = workflowId;
        this.workflowName = workflowName;
        this.numberOfArrangements = numberOfArrangements;
    }

    public Long getAdminId() { return adminId; }
    public String getAdministrator() { return administrator; }
    public Long getWorkflowId() { return workflowId; }
    public String getWorkflowName() { return workflowName; }
    public Long getNumberOfArrangements() { return numberOfArrangements; }

    public void setAdminId(Long adminId) { this.adminId = adminId; }
    public void setAdministrator(String administrator) { this.administrator = administrator; }
    public void setWorkflowId(Long workflowId) { this.workflowId = workflowId; }
    public void setWorkflowName(String workflowName) { this.workflowName = workflowName; }
    public void setNumberOfArrangements(Long numberOfArrangements) { this.numberOfArrangements = numberOfArrangements; }
}