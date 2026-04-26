package rs.ac.uns.acs.nais.workflow_service.dto;

public class BetterAccommodationOfferDTO {

    private String administrator;
    private String arrangement;
    private Long originalOfferId;
    private String originalAccommodation;
    private Double originalRating;
    private Long betterOfferId;
    private String betterAccommodation;
    private Double betterRating;

    public BetterAccommodationOfferDTO() {}

    public BetterAccommodationOfferDTO(String administrator, String arrangement,
                                       Long originalOfferId, String originalAccommodation, Double originalRating,
                                       Long betterOfferId, String betterAccommodation, Double betterRating) {
        this.administrator = administrator;
        this.arrangement = arrangement;
        this.originalOfferId = originalOfferId;
        this.originalAccommodation = originalAccommodation;
        this.originalRating = originalRating;
        this.betterOfferId = betterOfferId;
        this.betterAccommodation = betterAccommodation;
        this.betterRating = betterRating;
    }

    public String getAdministrator() { return administrator; }
    public void setAdministrator(String administrator) { this.administrator = administrator; }

    public String getArrangement() { return arrangement; }
    public void setArrangement(String arrangement) { this.arrangement = arrangement; }

    public Long getOriginalOfferId() { return originalOfferId; }
    public void setOriginalOfferId(Long originalOfferId) { this.originalOfferId = originalOfferId; }

    public String getOriginalAccommodation() { return originalAccommodation; }
    public void setOriginalAccommodation(String originalAccommodation) { this.originalAccommodation = originalAccommodation; }

    public Double getOriginalRating() { return originalRating; }
    public void setOriginalRating(Double originalRating) { this.originalRating = originalRating; }

    public Long getBetterOfferId() { return betterOfferId; }
    public void setBetterOfferId(Long betterOfferId) { this.betterOfferId = betterOfferId; }

    public String getBetterAccommodation() { return betterAccommodation; }
    public void setBetterAccommodation(String betterAccommodation) { this.betterAccommodation = betterAccommodation; }

    public Double getBetterRating() { return betterRating; }
    public void setBetterRating(Double betterRating) { this.betterRating = betterRating; }
}