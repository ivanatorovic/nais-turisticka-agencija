package com.example.analiza_aktivnosti.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("registrations_by_status")
public class RegistrationByStatus {

    @PrimaryKeyColumn(name = "status", type = PrimaryKeyType.PARTITIONED)
    private String status;

    @PrimaryKeyColumn(
            name = "registration_date",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 0
    )
    private LocalDate registrationDate;

    @PrimaryKeyColumn(
            name = "registration_id",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 1
    )
    private Long registrationId;

    @Column("activity_id")
    private Long activityId;

    @Column("activity_name")
    private String activityName;

    @Column("customer_id")
    private Long customerId;

    @Column("customer_name")
    private String customerName;

    @Column("number_of_people")
    private Integer numberOfPeople;

    @Column("total_price")
    private BigDecimal totalPrice;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}