package com.example.analiza_prodaje.model;



import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("prodaja_aranzmana_po_mesecu")
public class ProdajaAranzmanaPoMesecu {

    @PrimaryKeyColumn(name = "aranzman_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID aranzmanId;

    @PrimaryKeyColumn(name = "godina", type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private Integer godina;

    @PrimaryKeyColumn(name = "mesec", type = PrimaryKeyType.CLUSTERED, ordinal = 2, ordering = Ordering.ASCENDING)
    private Integer mesec;

    @PrimaryKeyColumn(name = "rezervacija_id", type = PrimaryKeyType.CLUSTERED, ordinal = 3, ordering = Ordering.ASCENDING)
    private UUID rezervacijaId;

    @Column("korisnik_id")
    private UUID korisnikId;

    @Column("naziv_aranzmana")
    private String nazivAranzmana;

    @Column("destinacija")
    private String destinacija;

    @Column("broj_osoba")
    private Integer brojOsoba;

    @Column("ukupna_cena")
    private Double ukupnaCena;

    @Column("datum_rezervacije")
    private LocalDateTime datumRezervacije;

    public UUID getAranzmanId() {
        return aranzmanId;
    }

    public void setAranzmanId(UUID aranzmanId) {
        this.aranzmanId = aranzmanId;
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    public Integer getMesec() {
        return mesec;
    }

    public void setMesec(Integer mesec) {
        this.mesec = mesec;
    }

    public UUID getRezervacijaId() {
        return rezervacijaId;
    }

    public void setRezervacijaId(UUID rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public UUID getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(UUID korisnikId) {
        this.korisnikId = korisnikId;
    }

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

    public LocalDateTime getDatumRezervacije() {
        return datumRezervacije;
    }

    public void setDatumRezervacije(LocalDateTime datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }
}
