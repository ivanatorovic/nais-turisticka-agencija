package rs.ac.uns.acs.nais.recommendation_service.dto;

public class BookingSagaRequestDTO {

    private Long reservationId;
    private Long userId;
    private Long arrangementId;
    private String customerName;
    private Integer persons;
    private Double totalPrice;

    public Long getReservationId() { return reservationId; }
    public Long getUserId() { return userId; }
    public Long getArrangementId() { return arrangementId; }
    public String getCustomerName() { return customerName; }
    public Integer getPersons() { return persons; }
    public Double getTotalPrice() { return totalPrice; }
}