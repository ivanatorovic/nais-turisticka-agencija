package com.turisticka_agencija.dodatne_aktivnosti.model;

import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDate;

@RelationshipProperties
public class Registration {

    @RelationshipId
    private Long id;

    private Long registrationId;

    @TargetNode
    private AdditionalActivity activity;

    private LocalDate registrationDate;
    private Integer numberOfPeople;

    public Registration() {
    }

    public Registration(Long registrationId, AdditionalActivity activity, LocalDate registrationDate, Integer numberOfPeople) {
        this.registrationId = registrationId;
        this.activity = activity;
        this.registrationDate = registrationDate;
        this.numberOfPeople = numberOfPeople;
    }

    public Long getId() {
        return id;
    }

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public AdditionalActivity getActivity() {
        return activity;
    }

    public void setActivity(AdditionalActivity activity) {
        this.activity = activity;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(Integer numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}