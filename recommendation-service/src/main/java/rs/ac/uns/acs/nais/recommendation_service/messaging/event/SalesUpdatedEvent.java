package rs.ac.uns.acs.nais.recommendation_service.messaging.event;

public class SalesUpdatedEvent {

    private String sagaId;
    private Long reservationId;
    private Long userId;
    private Long arrangementId;

    public SalesUpdatedEvent() {
    }

    public String getSagaId() { return sagaId; }
    public Long getReservationId() { return reservationId; }
    public Long getUserId() { return userId; }
    public Long getArrangementId() { return arrangementId; }
}