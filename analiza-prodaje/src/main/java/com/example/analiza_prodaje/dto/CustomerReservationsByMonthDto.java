package com.example.analiza_prodaje.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomerReservationsByMonthDto {

    private Long customerId;
    private String month;
    private Long reservationId;
    private String customerName;
    private Long arrangementId;
    private String arrangementName;
    private String destination;
    private LocalDate reservationDate;
    private Integer numberOfPeople;
    private BigDecimal totalPrice;

    public CustomerReservationsByMonthDto() {
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