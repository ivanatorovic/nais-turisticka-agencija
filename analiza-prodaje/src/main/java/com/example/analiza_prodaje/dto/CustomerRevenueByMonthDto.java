package com.example.analiza_prodaje.dto;

import java.math.BigDecimal;

public class CustomerRevenueByMonthDto {

    private Long customerId;
    private String month;
    private BigDecimal totalRevenue;

    public CustomerRevenueByMonthDto() {
    }

    public CustomerRevenueByMonthDto(
            Long customerId,
            String month,
            BigDecimal totalRevenue) {
        this.customerId = customerId;
        this.month = month;
        this.totalRevenue = totalRevenue;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}