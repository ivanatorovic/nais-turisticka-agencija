package com.example.analiza_prodaje.repository;

import com.example.analiza_prodaje.model.RezervacijaKorisnika;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface RezervacijaKorisnikaRepository extends CassandraRepository<RezervacijaKorisnika, UUID> {

    @Query("SELECT * FROM rezervacije_korisnika WHERE korisnik_id = ?0")
    List<RezervacijaKorisnika> findByKorisnikId(UUID korisnikId);

    @Query("SELECT * FROM rezervacije_korisnika WHERE korisnik_id = ?0 AND datum_rezervacije = ?1 AND rezervacija_id = ?2")
    RezervacijaKorisnika findOne(UUID korisnikId, LocalDateTime datumRezervacije, UUID rezervacijaId);

    @Query("DELETE FROM rezervacije_korisnika WHERE korisnik_id = ?0 AND datum_rezervacije = ?1 AND rezervacija_id = ?2")
    void deleteReservation(UUID korisnikId, LocalDateTime datumRezervacije, UUID rezervacijaId);
}