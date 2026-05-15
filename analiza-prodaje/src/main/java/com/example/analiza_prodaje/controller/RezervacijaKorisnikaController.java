package com.example.analiza_prodaje.controller;

import com.example.analiza_prodaje.dto.UpdateRezervacijaKorisnikaDTO;
import com.example.analiza_prodaje.model.RezervacijaKorisnika;
import com.example.analiza_prodaje.service.RezervacijaKorisnikaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rezervacije-korisnika")
public class RezervacijaKorisnikaController {

    @Autowired
    private RezervacijaKorisnikaService service;

    @GetMapping
    public ResponseEntity<List<RezervacijaKorisnika>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<RezervacijaKorisnika> create(@RequestBody RezervacijaKorisnika rezervacija) {
        return new ResponseEntity<>(service.create(rezervacija), HttpStatus.CREATED);
    }

    @GetMapping("/korisnik/{korisnikId}")
    public ResponseEntity<List<RezervacijaKorisnika>> findByKorisnikId(@PathVariable UUID korisnikId) {
        return ResponseEntity.ok(service.findByKorisnikId(korisnikId));
    }

    @GetMapping("/{korisnikId}/{datumRezervacije}/{rezervacijaId}")
    public ResponseEntity<RezervacijaKorisnika> findOne(
            @PathVariable UUID korisnikId,
            @PathVariable LocalDateTime datumRezervacije,
            @PathVariable UUID rezervacijaId
    ) {
        return ResponseEntity.ok(service.findOne(korisnikId, datumRezervacije, rezervacijaId));
    }

    @PatchMapping("/{korisnikId}/{datumRezervacije}/{rezervacijaId}")
    public ResponseEntity<RezervacijaKorisnika> patchUpdate(
            @PathVariable UUID korisnikId,
            @PathVariable LocalDateTime datumRezervacije,
            @PathVariable UUID rezervacijaId,
            @RequestBody UpdateRezervacijaKorisnikaDTO dto
    ) {
        return ResponseEntity.ok(service.patchUpdate(korisnikId, datumRezervacije, rezervacijaId, dto));
    }

    @DeleteMapping("/{korisnikId}/{datumRezervacije}/{rezervacijaId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID korisnikId,
            @PathVariable LocalDateTime datumRezervacije,
            @PathVariable UUID rezervacijaId
    ) {
        service.delete(korisnikId, datumRezervacije, rezervacijaId);
        return ResponseEntity.noContent().build();
    }
}