package rs.ac.uns.acs.nais.recommendation_service.dto;

import java.util.List;

public class ArrangementResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationDays;
    private DestinationResponse destination;
    private List<TagResponse> tags;

    public ArrangementResponse() {
    }

    public ArrangementResponse(Long id, String name, String description, Double price, Integer durationDays,
                               DestinationResponse destination, List<TagResponse> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
        this.destination = destination;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public DestinationResponse getDestination() {
        return destination;
    }

    public void setDestination(DestinationResponse destination) {
        this.destination = destination;
    }

    public List<TagResponse> getTags() {
        return tags;
    }

    public void setTags(List<TagResponse> tags) {
        this.tags = tags;
    }
}