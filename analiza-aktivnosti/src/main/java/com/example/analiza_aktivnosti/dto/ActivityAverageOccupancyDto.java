package com.example.analiza_aktivnosti.dto;

public class ActivityAverageOccupancyDto {

    private Long activityId;
    private Double averageOccupancyPercent;

    public ActivityAverageOccupancyDto() {
    }

    public ActivityAverageOccupancyDto(Long activityId, Double averageOccupancyPercent) {
        this.activityId = activityId;
        this.averageOccupancyPercent = averageOccupancyPercent;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Double getAverageOccupancyPercent() {
        return averageOccupancyPercent;
    }

    public void setAverageOccupancyPercent(Double averageOccupancyPercent) {
        this.averageOccupancyPercent = averageOccupancyPercent;
    }
}