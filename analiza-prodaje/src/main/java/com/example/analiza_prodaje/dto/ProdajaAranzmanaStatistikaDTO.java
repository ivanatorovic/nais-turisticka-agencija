package com.example.analiza_prodaje.dto;



public class ProdajaAranzmanaStatistikaDTO {

    private Integer mesec;
    private Long brojRezervacija;
    private Double ukupanPrihod;

    public ProdajaAranzmanaStatistikaDTO() {
    }

    public ProdajaAranzmanaStatistikaDTO(Integer mesec, Long brojRezervacija, Double ukupanPrihod) {
        this.mesec = mesec;
        this.brojRezervacija = brojRezervacija;
        this.ukupanPrihod = ukupanPrihod;
    }

    public Integer getMesec() {
        return mesec;
    }

    public void setMesec(Integer mesec) {
        this.mesec = mesec;
    }

    public Long getBrojRezervacija() {
        return brojRezervacija;
    }

    public void setBrojRezervacija(Long brojRezervacija) {
        this.brojRezervacija = brojRezervacija;
    }

    public Double getUkupanPrihod() {
        return ukupanPrihod;
    }

    public void setUkupanPrihod(Double ukupanPrihod) {
        this.ukupanPrihod = ukupanPrihod;
    }
}
