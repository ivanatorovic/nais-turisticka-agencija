package com.example.analiza_prodaje.dto;

import java.io.Serializable;

public class TermPeopleDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long arrangementId;
    private Long termId;
    private Integer totalPeople;

    public TermPeopleDto() {
    }

    public TermPeopleDto(
            Long arrangementId,
            Long termId,
            Integer totalPeople
    ) {
        this.arrangementId = arrangementId;
        this.termId = termId;
        this.totalPeople = totalPeople;
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

    public Integer getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(Integer totalPeople) {
        this.totalPeople = totalPeople;
    }
}