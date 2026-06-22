package com.example.analiza_prodaje.messaging.event;

public class SalesUpdatedEvent {

    private String sagaId;
    private Long reservationId;
    private Long userId;
    private Long arrangementId;

    public SalesUpdatedEvent() {
    }

    public SalesUpdatedEvent(String sagaId, Long reservationId, Long userId, Long arrangementId) {
        this.sagaId = sagaId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.arrangementId = arrangementId;
    }

    public String getSagaId() { return sagaId; }
    public Long getReservationId() { return reservationId; }
    public Long getUserId() { return userId; }
    public Long getArrangementId() { return arrangementId; }
}