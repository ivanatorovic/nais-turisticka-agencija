package com.example.analiza_prodaje.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Table("destination_sales_by_month")
public class DestinationSalesByMonth {

    @PrimaryKeyColumn(
            name = "month",
            type = PrimaryKeyType.PARTITIONED
    )
    private String month;

    @PrimaryKeyColumn(
            name = "destination",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 0
    )
    private String destination;

    @PrimaryKeyColumn(
            name = "reservation_id",
            type = PrimaryKeyType.CLUSTERED,
            ordinal = 1
    )
    private Long reservationId;

    @Column("arrangement_id")
    private Long arrangementId;

    @Column("arrangement_name")
    private String arrangementName;

    @Column("customer_id")
    private Long customerId;

    @Column("customer_name")
    private String customerName;

    @Column("number_of_people")
    private Integer numberOfPeople;

    @Column("total_price")
    private BigDecimal totalPrice;

    public DestinationSalesByMonth() {
    }

    public String getMonth() {
        return month;
    }

    public String getDestination() {
        return destination;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public String getArrangementName() {
        return arrangementName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public void setArrangementName(String arrangementName) {
        this.arrangementName = arrangementName;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}