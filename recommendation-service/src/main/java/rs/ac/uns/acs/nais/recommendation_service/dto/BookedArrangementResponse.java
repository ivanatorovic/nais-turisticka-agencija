package rs.ac.uns.acs.nais.recommendation_service.dto;

public class BookedArrangementResponse {
    private Long arrangementId;
    private String arrangementName;
    private String bookingDate;
    private Integer persons;
    private Double totalPrice;
    private Integer count;

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getArrangementName() {
        return arrangementName;
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

    public Integer getCount() {
        return count;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public void setArrangementName(String arrangementName) {
        this.arrangementName = arrangementName;
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

    public void setCount(Integer count) {
        this.count = count;
    }
}
