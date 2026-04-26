package rs.ac.uns.acs.nais.workflow_service.dto;

public class OfferAverageRatingDTO {

    private Long arrangementId;
    private String arrangement;
    private Long offerId;
    private Double averageRating;
    private Long numberOfRatings;

    public OfferAverageRatingDTO() {}

    public OfferAverageRatingDTO(Long arrangementId, String arrangement, Long offerId,
                                 Double averageRating, Long numberOfRatings) {
        this.arrangementId = arrangementId;
        this.arrangement = arrangement;
        this.offerId = offerId;
        this.averageRating = averageRating;
        this.numberOfRatings = numberOfRatings;
    }

    public Long getArrangementId() { return arrangementId; }
    public String getArrangement() { return arrangement; }
    public Long getOfferId() { return offerId; }
    public Double getAverageRating() { return averageRating; }
    public Long getNumberOfRatings() { return numberOfRatings; }

    public void setArrangementId(Long arrangementId) { this.arrangementId = arrangementId; }
    public void setArrangement(String arrangement) { this.arrangement = arrangement; }
    public void setOfferId(Long offerId) { this.offerId = offerId; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    public void setNumberOfRatings(Long numberOfRatings) { this.numberOfRatings = numberOfRatings; }
}