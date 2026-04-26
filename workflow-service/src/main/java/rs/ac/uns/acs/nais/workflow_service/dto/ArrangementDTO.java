package rs.ac.uns.acs.nais.workflow_service.dto;

public class ArrangementDTO {

    private Long id;
    private String name;
    private String description;
    private String destination;

    public ArrangementDTO() {}

    public ArrangementDTO(Long id, String name, String description, String destination) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDestination() {
        return destination;
    }
}