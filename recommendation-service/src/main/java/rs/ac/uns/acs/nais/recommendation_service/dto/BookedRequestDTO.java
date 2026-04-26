package rs.ac.uns.acs.nais.recommendation_service.dto;

public class BookedRequestDTO {

    private Long userId;
    private Long arrangementId;
    private String bookingDate;
    private Integer persons;
    private Double totalPrice;

    public BookedRequestDTO() {
    }

    public BookedRequestDTO(Long userId, Long arrangementId, String bookingDate, Integer persons, Double totalPrice) {
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.bookingDate = bookingDate;
        this.persons = persons;
        this.totalPrice = totalPrice;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getBookingDate() {
        return bookingDate;
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

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}