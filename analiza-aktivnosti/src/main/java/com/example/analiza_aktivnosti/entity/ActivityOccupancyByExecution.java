package com.example.analiza_aktivnosti.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("activity_occupancy_by_execution")
public class ActivityOccupancyByExecution {

    @PrimaryKeyColumn(name = "activity_id", type = PrimaryKeyType.PARTITIONED)
    private Long activityId;

    @PrimaryKeyColumn(name = "execution_id", ordinal = 0)
    private Long executionId;

    @Column("activity_name")
    private String activityName;

    @Column("capacity")
    private Integer capacity;

    @Column("reserved_spots")
    private Integer reservedSpots;

    @Column("occupancy_percent")
    private Double occupancyPercent;

    @Column("status")
    private String status;

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public Long getExecutionId() { return executionId; }
    public void setExecutionId(Long executionId) { this.executionId = executionId; }

    public String getActivityName() { return activityName; }
    public void setActivityName(String activityName) { this.activityName = activityName; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getReservedSpots() { return reservedSpots; }
    public void setReservedSpots(Integer reservedSpots) { this.reservedSpots = reservedSpots; }

    public Double getOccupancyPercent() { return occupancyPercent; }
    public void setOccupancyPercent(Double occupancyPercent) { this.occupancyPercent = occupancyPercent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}