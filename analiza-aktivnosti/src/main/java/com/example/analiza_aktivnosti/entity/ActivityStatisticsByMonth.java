package com.example.analiza_aktivnosti.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Table("activity_statistics_by_month")
public class ActivityStatisticsByMonth {

    @PrimaryKeyColumn(name = "month", type = PrimaryKeyType.PARTITIONED)
    private String month;

    @PrimaryKeyColumn(name = "total_revenue", ordinal = 0)
    private BigDecimal totalRevenue;

    @PrimaryKeyColumn(name = "activity_id", ordinal = 1)
    private Long activityId;

    @Column("activity_name")
    private String activityName;

    @Column("total_people")
    private Integer totalPeople;

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public Integer getTotalPeople() { return totalPeople; }
    public void setTotalPeople(Integer totalPeople) { this.totalPeople = totalPeople; }
}