package rs.ac.uns.acs.nais.recommendation_service.dto;

public class BookedRequestDTO {

    private Long userId;
    private Long arrangementId;

    private Integer persons;
    private Double totalPrice;

    public BookedRequestDTO() {
    }

    public BookedRequestDTO(Long userId, Long arrangementId,  Integer persons, Double totalPrice) {
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.persons = persons;
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }



    public Integer getPersons() {
        return persons;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }



    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}