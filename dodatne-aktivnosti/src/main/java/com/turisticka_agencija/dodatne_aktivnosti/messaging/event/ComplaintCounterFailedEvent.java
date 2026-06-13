package com.turisticka_agencija.dodatne_aktivnosti.messaging.event;

public class ComplaintCounterFailedEvent {

    private String eventType;
    private String sagaId;
    private Long complaintId;
    private Long activityId;
    private Long customerId;
    private String reason;

    public ComplaintCounterFailedEvent(
            String sagaId,
            Long complaintId,
            Long activityId,
            Long customerId,
            String reason
    ) {
        this.eventType = "COMPLAINT_COUNTER_FAILED";
        this.sagaId = sagaId;
        this.complaintId = complaintId;
        this.activityId = activityId;
        this.customerId = customerId;
        this.reason = reason;
    }

    public String getEventType() {
        return eventType;
    }

    public String getSagaId() {
        return sagaId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getReason() {
        return reason;
    }
}