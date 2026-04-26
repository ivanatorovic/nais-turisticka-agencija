package rs.ac.uns.acs.nais.workflow_service.dto;

import rs.ac.uns.acs.nais.workflow_service.model.TransportType;

public class TransportDTO {

    private Long id;
    private TransportType type;
    private String company;
    private Double rating;

    public TransportDTO() {}

    public TransportDTO(Long id, TransportType type, String company, Double rating) {
        this.id = id;
        this.type = type;
        this.company = company;
        this.rating = rating;
    }

    public Long getId() { return id; }
    public TransportType getType() { return type; }
    public String getCompany() { return company; }
    public Double getRating() { return rating; }
}