package rs.ac.uns.acs.nais.recommendation_service.dto;

public class ArrangementRecommendationResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer durationDays;

    public ArrangementRecommendationResponse() {
    }

    public ArrangementRecommendationResponse(Long id, String name, String description, Double price, Integer durationDays) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.durationDays = durationDays;
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
}