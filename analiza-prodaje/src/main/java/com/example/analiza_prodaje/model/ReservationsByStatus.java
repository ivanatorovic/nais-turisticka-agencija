package com.example.analiza_prodaje.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("reservations_by_status")
public class ReservationsByStatus {

    @PrimaryKeyColumn(name = "status", type = PrimaryKeyType.PARTITIONED)
    private String status;

    @PrimaryKeyColumn(name = "reservation_date", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private LocalDate reservationDate;

    @PrimaryKeyColumn(name = "reservation_id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private Long reservationId;

    @Column("arrangement_id")
    private Long arrangementId;

    @Column("arrangement_name")
    private String arrangementName;

    @Column("destination")
    private String destination;

    @Column("customer_id")
    private Long customerId;

    @Column("customer_name")
    private String customerName;

    @Column("number_of_people")
    private Integer numberOfPeople;

    @Column("total_price")
    private BigDecimal totalPrice;

    public ReservationsByStatus() {}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public Long getArrangementId() { return arrangementId; }
    public void setArrangementId(Long arrangementId) { this.arrangementId = arrangementId; }

    public String getArrangementName() { return arrangementName; }
    public void setArrangementName(String arrangementName) { this.arrangementName = arrangementName; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}