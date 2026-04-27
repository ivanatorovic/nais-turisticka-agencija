package rs.ac.uns.acs.nais.workflow_service.dto;

import java.util.List;

public class HotelFacilitiesDTO {
    private Long accommodationId;
    private String hotel;
    private Double rating;
    private Integer numberOfFacilities;
    private List<String> facilities;

    public HotelFacilitiesDTO(Long accommodationId,
                              String hotel,
                              Double rating,
                              Integer numberOfFacilities,
                              List<String> facilities) {
        this.accommodationId = accommodationId;
        this.hotel = hotel;
        this.rating = rating;
        this.numberOfFacilities = numberOfFacilities;
        this.facilities = facilities;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public String getHotel() {
        return hotel;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getNumberOfFacilities() {
        return numberOfFacilities;
    }

    public List<String> getFacilities() {
        return facilities;
    }
}
