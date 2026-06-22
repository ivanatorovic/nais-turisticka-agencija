package rs.ac.uns.acs.nais.recommendation_service.messaging.event;

public class SalesFailedEvent {

    private String sagaId;
    private Long userId;
    private Long arrangementId;
    private String reason;

    public SalesFailedEvent() {
    }

    public String getSagaId() { return sagaId; }
    public Long getUserId() { return userId; }
    public Long getArrangementId() { return arrangementId; }
    public String getReason() { return reason; }
}