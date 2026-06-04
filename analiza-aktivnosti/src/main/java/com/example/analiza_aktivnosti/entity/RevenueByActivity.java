package com.example.analiza_aktivnosti.entity;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("revenue_by_activity")
public class RevenueByActivity {

    @PrimaryKeyColumn(name = "activity_id", type = PrimaryKeyType.PARTITIONED)
    private Long activityId;

    @PrimaryKeyColumn(name = "registration_id", ordinal = 0)
    private Long registrationId;

    @Column("registration_date")
    private LocalDateTime registrationDate;

    @Column("customer_id")
    private Long customerId;

    @Column("customer_name")
    private String customerName;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("activity_name")
    private String activityName;

    @Column("number_of_people")
    private Integer numberOfPeople;

    public Long getActivityId() { return activityId; }
    public void setActivityId(Long activityId) { this.activityId = activityId; }

    public Long getRegistrationId() { return registrationId; }
    public void setRegistrationId(Long registrationId) { this.registrationId = registrationId; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}