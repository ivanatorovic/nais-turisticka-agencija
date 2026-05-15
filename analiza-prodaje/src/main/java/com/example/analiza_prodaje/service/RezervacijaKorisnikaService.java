package com.example.analiza_prodaje.service;

import com.example.analiza_prodaje.dto.UpdateRezervacijaKorisnikaDTO;
import com.example.analiza_prodaje.model.RezervacijaKorisnika;
import com.example.analiza_prodaje.repository.RezervacijaKorisnikaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RezervacijaKorisnikaService {

    @Autowired
    private RezervacijaKorisnikaRepository repository;

    public List<RezervacijaKorisnika> findAll() {
        return repository.findAll();
    }

    public RezervacijaKorisnika create(RezervacijaKorisnika rezervacija) {
        if (rezervacija.getRezervacijaId() == null) {
            rezervacija.setRezervacijaId(UUID.randomUUID());
        }

        if (rezervacija.getDatumRezervacije() == null) {
            rezervacija.setDatumRezervacije(LocalDateTime.now());
        }

        return repository.save(rezervacija);
    }

    public List<RezervacijaKorisnika> findByKorisnikId(UUID korisnikId) {
        return repository.findByKorisnikId(korisnikId);
    }

    public RezervacijaKorisnika findOne(UUID korisnikId, LocalDateTime datumRezervacije, UUID rezervacijaId) {
        RezervacijaKorisnika rezervacija = repository.findOne(korisnikId, datumRezervacije, rezervacijaId);

        if (rezervacija == null) {
            throw new RuntimeException("Rezervacija korisnika nije pronađena.");
        }

        return rezervacija;
    }

    public RezervacijaKorisnika patchUpdate(
            UUID korisnikId,
            LocalDateTime datumRezervacije,
            UUID rezervacijaId,
            UpdateRezervacijaKorisnikaDTO dto
    ) {
        RezervacijaKorisnika rezervacija = findOne(korisnikId, datumRezervacije, rezervacijaId);

        if (dto.getNazivAranzmana() != null) {
            rezervacija.setNazivAranzmana(dto.getNazivAranzmana());
        }

        if (dto.getDestinacija() != null) {
            rezervacija.setDestinacija(dto.getDestinacija());
        }

        if (dto.getBrojOsoba() != null) {
            rezervacija.setBrojOsoba(dto.getBrojOsoba());
        }

        if (dto.getUkupnaCena() != null) {
            rezervacija.setUkupnaCena(dto.getUkupnaCena());
        }

        if (dto.getStatus() != null) {
            rezervacija.setStatus(dto.getStatus());
        }

        return repository.save(rezervacija);
    }

    public void delete(UUID korisnikId, LocalDateTime datumRezervacije, UUID rezervacijaId) {
        repository.deleteReservation(korisnikId, datumRezervacije, rezervacijaId);
    }
}