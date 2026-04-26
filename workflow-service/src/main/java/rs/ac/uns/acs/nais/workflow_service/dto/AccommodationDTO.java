package rs.ac.uns.acs.nais.workflow_service.dto;

import rs.ac.uns.acs.nais.workflow_service.model.AccommodationType;

public class AccommodationDTO {

    private Long id;
    private String name;
    private AccommodationType type;
    private Double rating;

    public AccommodationDTO() {
    }

    public AccommodationDTO(Long id, String name, AccommodationType type, Double rating) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccommodationType getType() {
        return type;
    }

    public Double getRating() {
        return rating;
    }
}