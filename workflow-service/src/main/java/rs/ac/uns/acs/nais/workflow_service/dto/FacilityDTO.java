package rs.ac.uns.acs.nais.workflow_service.dto;

public class FacilityDTO {

    private Long id;
    private String name;

    public FacilityDTO() {}

    public FacilityDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
}