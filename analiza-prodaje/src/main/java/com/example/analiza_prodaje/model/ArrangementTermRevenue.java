package com.example.analiza_prodaje.model;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table("arrangement_term_revenue")
public class ArrangementTermRevenue {

    @PrimaryKeyColumn(name = "arrangement_id", type = PrimaryKeyType.PARTITIONED)
    private Long arrangementId;

    @PrimaryKeyColumn(name = "term_id", type = PrimaryKeyType.CLUSTERED, ordinal = 0)
    private Long termId;

    @PrimaryKeyColumn(name = "reservation_id", type = PrimaryKeyType.CLUSTERED, ordinal = 1)
    private Long reservationId;

    @Column("arrangement_name")
    private String arrangementName;

    @Column("term_start_date")
    private LocalDate termStartDate;

    @Column("term_end_date")
    private LocalDate termEndDate;

    @Column("customer_id")
    private Long customerId;

    @Column("customer_name")
    private String customerName;

    @Column("number_of_people")
    private Integer numberOfPeople;

    @Column("total_price")
    private BigDecimal totalPrice;

    public ArrangementTermRevenue() {
    }

    public Long getArrangementId() { return arrangementId; }
    public void setArrangementId(Long arrangementId) { this.arrangementId = arrangementId; }

    public Long getTermId() { return termId; }
    public void setTermId(Long termId) { this.termId = termId; }

    public Long getReservationId() { return reservationId; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }

    public String getArrangementName() { return arrangementName; }
    public void setArrangementName(String arrangementName) { this.arrangementName = arrangementName; }

    public LocalDate getTermStartDate() { return termStartDate; }
    public void setTermStartDate(LocalDate termStartDate) { this.termStartDate = termStartDate; }

    public LocalDate getTermEndDate() { return termEndDate; }
    public void setTermEndDate(LocalDate termEndDate) { this.termEndDate = termEndDate; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Integer getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(Integer numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}