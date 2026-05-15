package com.example.analiza_prodaje.dto;

public class UpdateRezervacijaKorisnikaDTO {

    private String nazivAranzmana;
    private String destinacija;
    private Integer brojOsoba;
    private Double ukupnaCena;
    private String status;

    // getteri i setteri

    public void setNazivAranzmana(String nazivAranzmana) {
        this.nazivAranzmana = nazivAranzmana;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    public void setBrojOsoba(Integer brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public void setUkupnaCena(Double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNazivAranzmana() {
        return nazivAranzmana;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public Integer getBrojOsoba() {
        return brojOsoba;
    }

    public Double getUkupnaCena() {
        return ukupnaCena;
    }

    public String getStatus() {
        return status;
    }
}