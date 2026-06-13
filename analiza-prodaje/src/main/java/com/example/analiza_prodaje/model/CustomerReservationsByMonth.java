package com.example.analiza_prodaje.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("customer_reservations_by_month")
public class CustomerReservationsByMonth {

    @PrimaryKeyColumn(name = "customer_id", type = PrimaryKeyType.PARTITIONED)
    private Long customerId;

    @PrimaryKeyColumn(name = "month", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private String month;

    @PrimaryKeyColumn(name = "reservation_id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private Long reservationId;

    @Column("customer_name")
    private String customerName;

    @Column("arrangement_id")
    private Long arrangementId;

    @Column("arrangement_name")
    private String arrangementName;

    @Column("destination")
    private String destination;

    @Column("reservation_date")
    private LocalDate reservationDate;

    @Column("number_of_people")
    private Integer numberOfPeople;

    @Column("total_price")
    private BigDecimal totalPrice;

    public CustomerReservationsByMonth() {
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Long getArrangementId() { return arrangementId; }
    public void setArrangementId(Long arrangementId) { this.arrangementId = arrangementId; }

    public String getArrangementName() { return arrangementName; }
    public void setArrangementName(String arrangementName) { this.arrangementName = arrangementName; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDate getReservationDate() { return reservationDate; }
    public void setReservationDate(LocalDate reservationDate) { this.reservationDate = reservationDate; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}