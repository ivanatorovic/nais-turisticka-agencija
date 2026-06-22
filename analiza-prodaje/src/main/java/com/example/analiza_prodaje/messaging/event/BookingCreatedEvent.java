package com.example.analiza_prodaje.messaging.event;

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