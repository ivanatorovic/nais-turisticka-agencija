package com.example.analiza_prodaje.messaging.event;

public class SalesFailedEvent {

    private String sagaId;
    private Long reservationId;
    private Long userId;
    private Long arrangementId;
    private String reason;

    public SalesFailedEvent() {
    }

    public SalesFailedEvent(
            String sagaId,
            Long reservationId,
            Long userId,
            Long arrangementId,
            String reason
    ) {
        this.sagaId = sagaId;
        this.reservationId = reservationId;
        this.userId = userId;
        this.arrangementId = arrangementId;
        this.reason = reason;
    }

    public String getSagaId() { return sagaId; }
    public Long getReservationId() { return reservationId; }
    public Long getUserId() { return userId; }
    public Long getArrangementId() { return arrangementId; }
    public String getReason() { return reason; }
}