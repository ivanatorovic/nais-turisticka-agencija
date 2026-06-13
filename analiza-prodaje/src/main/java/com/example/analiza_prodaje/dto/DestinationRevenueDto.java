package com.example.analiza_prodaje.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DestinationRevenueDto implements Serializable {

    private static final long serialVersionUID = 1L;

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