package com.example.analiza_aktivnosti.dto;

import java.math.BigDecimal;

public class ArrangementRevenueDto {

    private Long activityId;
    private String activityName;
    private BigDecimal totalRevenue;

    public ArrangementRevenueDto() {
    }

    public ArrangementRevenueDto(
            Long activityId,
            String activityName,
            BigDecimal totalRevenue
    ) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.totalRevenue = totalRevenue;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}