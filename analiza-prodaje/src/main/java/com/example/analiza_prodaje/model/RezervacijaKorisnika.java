package com.example.analiza_prodaje.model;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("rezervacije_korisnika")
public class RezervacijaKorisnika {

    @PrimaryKeyColumn(name = "korisnik_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private UUID korisnikId;

    @PrimaryKeyColumn(name = "datum_rezervacije", type = PrimaryKeyType.CLUSTERED, ordinal = 1, ordering = Ordering.DESCENDING)
    private LocalDateTime datumRezervacije;

    @PrimaryKeyColumn(name = "rezervacija_id", type = PrimaryKeyType.CLUSTERED, ordinal = 2, ordering = Ordering.ASCENDING)
    private UUID rezervacijaId;

    @Column("aranzman_id")
    private UUID aranzmanId;

    @Column("naziv_aranzmana")
    private String nazivAranzmana;

    private String destinacija;

    @Column("broj_osoba")
    private Integer brojOsoba;

    @Column("ukupna_cena")
    private Double ukupnaCena;

    private String status;

    // getteri i setteri

    public void setKorisnikId(UUID korisnikId) {
        this.korisnikId = korisnikId;
    }

    public void setDatumRezervacije(LocalDateTime datumRezervacije) {
        this.datumRezervacije = datumRezervacije;
    }

    public void setRezervacijaId(UUID rezervacijaId) {
        this.rezervacijaId = rezervacijaId;
    }

    public void setAranzmanId(UUID aranzmanId) {
        this.aranzmanId = aranzmanId;
    }

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

    public UUID getKorisnikId() {
        return korisnikId;
    }

    public LocalDateTime getDatumRezervacije() {
        return datumRezervacije;
    }

    public UUID getRezervacijaId() {
        return rezervacijaId;
    }

    public UUID getAranzmanId() {
        return aranzmanId;
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