package com.example.analiza_prodaje.dto;

import java.math.BigDecimal;

public class TermRevenueDto {

    private Long arrangementId;
    private Long termId;
    private BigDecimal totalRevenue;

    public TermRevenueDto() {
    }

    public TermRevenueDto(Long arrangementId,
                          Long termId,
                          BigDecimal totalRevenue) {
        this.arrangementId = arrangementId;
        this.termId = termId;
        this.totalRevenue = totalRevenue;
    }

    public Long getArrangementId() {
        return arrangementId;
    }

    public void setArrangementId(Long arrangementId) {
        this.arrangementId = arrangementId;
    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}