package rs.ac.uns.acs.nais.recommendation_service.messaging.event;

public class BookingCreatedEvent {

    private String sagaId;
    private Long reservationId;
    private Long userId;
    private Long arrangementId;
    private String arrangementName;
    private String destination;
    private String customerName;
    private Integer persons;
    private Double totalPrice;

    public BookingCreatedEvent() {
    }

    public BookingCreatedEvent(
            String sagaId,
            Long reservationId,
            Long userId,
            Long arrangementId,
            String arrangementName,
            String destination,
            String customerName,
            Integer persons,
            Double totalPrice
    ) {
        this.sagaId = sagaId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.arrangementName = arrangementName;
        this.destination = destination;
        this.customerName = customerName;
        this.persons = persons;
        this.totalPrice = totalPrice;
    }

    public String getSagaId() { return sagaId; }
    public Long getReservationId() { return reservationId; }
    public Long getUserId() { return userId; }
    public Long getArrangementId() { return arrangementId; }
    public String getArrangementName() { return arrangementName; }
    public String getDestination() { return destination; }
    public String getCustomerName() { return customerName; }
    public Integer getPersons() { return persons; }
    public Double getTotalPrice() { return totalPrice; }
}