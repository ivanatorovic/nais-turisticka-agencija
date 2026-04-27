package rs.ac.uns.acs.nais.workflow_service.dto;

import java.util.List;

public class HotelMealDTO {
    private Long accommodationId;
    private String hotel;
    private Double rating;
    private List<String> mealFacilities;
    private List<String> arrangements;

    public HotelMealDTO(Long accommodationId,
                        String hotel,
                        Double rating,
                        List<String> mealFacilities,
                        List<String> arrangements) {
        this.accommodationId = accommodationId;
        this.hotel = hotel;
        this.rating = rating;
        this.mealFacilities = mealFacilities;
        this.arrangements = arrangements;
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

    public List<String> getMealFacilities() {
        return mealFacilities;
    }

    public List<String> getArrangements() {
        return arrangements;
    }
}
