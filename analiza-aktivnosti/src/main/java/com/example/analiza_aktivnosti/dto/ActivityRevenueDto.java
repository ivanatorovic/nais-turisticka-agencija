package com.example.analiza_aktivnosti.dto;

import java.math.BigDecimal;

public class ActivityRevenueDto {

    private Long activityId;
    private BigDecimal totalRevenue;

    public ActivityRevenueDto() {
    }

    public ActivityRevenueDto(Long activityId, BigDecimal totalRevenue) {
        this.activityId = activityId;
        this.totalRevenue = totalRevenue;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}