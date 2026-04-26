package rs.ac.uns.acs.nais.workflow_service.dto;

import java.util.List;

public class HotelFacilitiesDTO {

    private Long accommodationId;
    private String hotel;
    private Double rating;
    private Long numberOfFacilities;
    private List<String> facilities;

    public HotelFacilitiesDTO() {}

    public HotelFacilitiesDTO(Long accommodationId, String hotel,
                              Double rating, Long numberOfFacilities,
                              List<String> facilities) {
        this.accommodationId = accommodationId;
        this.hotel = hotel;
        this.rating = rating;
        this.numberOfFacilities = numberOfFacilities;
        this.facilities = facilities;
    }

    public Long getAccommodationId() { return accommodationId; }
    public String getHotel() { return hotel; }
    public Double getRating() { return rating; }
    public Long getNumberOfFacilities() { return numberOfFacilities; }
    public List<String> getFacilities() { return facilities; }

    public void setAccommodationId(Long accommodationId) { this.accommodationId = accommodationId; }
    public void setHotel(String hotel) { this.hotel = hotel; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setNumberOfFacilities(Long numberOfFacilities) { this.numberOfFacilities = numberOfFacilities; }
    public void setFacilities(List<String> facilities) { this.facilities = facilities; }
}