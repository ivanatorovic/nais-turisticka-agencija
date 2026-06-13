package com.turisticka_agencija.dodatne_aktivnosti.messaging.event;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ComplaintCreatedEvent {

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("saga_id")
    private String sagaId;

    @JsonProperty("zalba_id")
    private Long complaintId;

    @JsonProperty("aktivnost_id")
    private Long activityId;

    @JsonProperty("putnik_id")
    private Long customerId;

    private String timestamp;

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

    public String getTimestamp() {
        return timestamp;
    }
}