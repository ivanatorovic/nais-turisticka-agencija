package com.example.analiza_prodaje.dto;

import java.math.BigDecimal;

public class DestinationRevenueDto {

    private String destination;
    private BigDecimal totalRevenue;

    public DestinationRevenueDto() {
    }

    public DestinationRevenueDto(
            String destination,
            BigDecimal totalRevenue
    ) {
        this.destination = destination;
        this.totalRevenue = totalRevenue;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}