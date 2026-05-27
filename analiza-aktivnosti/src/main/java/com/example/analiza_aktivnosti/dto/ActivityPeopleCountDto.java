package com.example.analiza_aktivnosti.dto;

public class ActivityPeopleCountDto {

    private Long activityId;
    private Long totalPeople;

    public ActivityPeopleCountDto() {
    }

    public ActivityPeopleCountDto(Long activityId, Long totalPeople) {
        this.activityId = activityId;
        this.totalPeople = totalPeople;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(Long totalPeople) {
        this.totalPeople = totalPeople;
    }
}