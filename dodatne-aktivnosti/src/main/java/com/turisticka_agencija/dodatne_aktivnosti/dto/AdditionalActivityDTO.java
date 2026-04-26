package com.turisticka_agencija.dodatne_aktivnosti.dto;

import java.time.LocalDate;

public class AdditionalActivityDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer maxCapacity;

    private ArrangementDTO arrangement;

    public AdditionalActivityDTO(Long id, String name, String description,
                                 Double price, String location,
                                 LocalDate startDate, LocalDate endDate,
                                 Integer maxCapacity,
                                 ArrangementDTO arrangement) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxCapacity = maxCapacity;
        this.arrangement = arrangement;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public ArrangementDTO getArrangement() {
        return arrangement;
    }
}