package rs.ac.uns.acs.nais.workflow_service.dto;

public class OfferDTO {

    private Long id;
    private String startDate;
    private String endDate;
    private Double priceForChildren;
    private Double priceForAdults;

    public OfferDTO() {
    }

    public OfferDTO(Long id, String startDate, String endDate, Double priceForChildren, Double priceForAdults) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.priceForChildren = priceForChildren;
        this.priceForAdults = priceForAdults;
    }

    public Long getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Double getPriceForChildren() {
        return priceForChildren;
    }

    public Double getPriceForAdults() {
        return priceForAdults;
    }
}