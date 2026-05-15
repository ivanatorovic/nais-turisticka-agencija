package com.example.analiza_prodaje.dto;



public class UpdateProdajaAranzmanaDTO {

    private String nazivAranzmana;
    private String destinacija;
    private Integer brojOsoba;
    private Double ukupnaCena;

    public String getNazivAranzmana() {
        return nazivAranzmana;
    }

    public void setNazivAranzmana(String nazivAranzmana) {
        this.nazivAranzmana = nazivAranzmana;
    }

    public String getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(String destinacija) {
        this.destinacija = destinacija;
    }

    public Integer getBrojOsoba() {
        return brojOsoba;
    }

    public void setBrojOsoba(Integer brojOsoba) {
        this.brojOsoba = brojOsoba;
    }

    public Double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(Double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }
}