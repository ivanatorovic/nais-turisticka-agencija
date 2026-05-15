package com.example.analiza_prodaje.service;



import com.example.analiza_prodaje.dto.ProdajaAranzmanaStatistikaDTO;
import com.example.analiza_prodaje.dto.UpdateProdajaAranzmanaDTO;
import com.example.analiza_prodaje.model.ProdajaAranzmanaPoMesecu;
import com.example.analiza_prodaje.repository.ProdajaAranzmanaPoMesecuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdajaAranzmanaPoMesecuService {

    @Autowired
    private ProdajaAranzmanaPoMesecuRepository repository;

    public List<ProdajaAranzmanaPoMesecu> findAll() {
        return repository.findAll();
    }

    public ProdajaAranzmanaPoMesecu create(ProdajaAranzmanaPoMesecu prodaja) {
        if (prodaja.getRezervacijaId() == null) {
            prodaja.setRezervacijaId(UUID.randomUUID());
        }

        if (prodaja.getDatumRezervacije() == null) {
            prodaja.setDatumRezervacije(LocalDateTime.now());
        }

        return repository.save(prodaja);
    }

    public ProdajaAranzmanaPoMesecu patchUpdate(
            UUID aranzmanId,
            Integer godina,
            Integer mesec,
            UUID rezervacijaId,
            UpdateProdajaAranzmanaDTO dto
    ) {
        ProdajaAranzmanaPoMesecu prodaja =
                repository.findOne(aranzmanId, godina, mesec, rezervacijaId);

        if (prodaja == null) {
            throw new RuntimeException("Rezervacija nije pronađena.");
        }

        if (dto.getNazivAranzmana() != null) {
            prodaja.setNazivAranzmana(dto.getNazivAranzmana());
        }

        if (dto.getDestinacija() != null) {
            prodaja.setDestinacija(dto.getDestinacija());
        }

        if (dto.getBrojOsoba() != null) {
            prodaja.setBrojOsoba(dto.getBrojOsoba());
        }

        if (dto.getUkupnaCena() != null) {
            prodaja.setUkupnaCena(dto.getUkupnaCena());
        }

        return repository.save(prodaja);
    }

    public ProdajaAranzmanaPoMesecu findOne(
            UUID aranzmanId,
            Integer godina,
            Integer mesec,
            UUID rezervacijaId
    ) {

        ProdajaAranzmanaPoMesecu prodaja =
                repository.findOne(
                        aranzmanId,
                        godina,
                        mesec,
                        rezervacijaId
                );

        if (prodaja == null) {
            throw new RuntimeException("Rezervacija nije pronađena.");
        }

        return prodaja;
    }

    public void delete(
            UUID aranzmanId,
            Integer godina,
            Integer mesec,
            UUID rezervacijaId
    ) {
        repository.deleteReservation(
                aranzmanId,
                godina,
                mesec,
                rezervacijaId
        );
    }

    public List<ProdajaAranzmanaPoMesecu> findByAranzmanAndGodina(UUID aranzmanId, Integer godina) {
        return repository.findByAranzmanAndGodina(aranzmanId, godina);
    }

    public List<ProdajaAranzmanaPoMesecu> findByAranzmanGodinaAndMesec(UUID aranzmanId, Integer godina, Integer mesec) {
        return repository.findByAranzmanGodinaAndMesec(aranzmanId, godina, mesec);
    }

    public List<ProdajaAranzmanaStatistikaDTO> getStatistikaPoMesecima(UUID aranzmanId, Integer godina) {
        List<Map<String, Object>> rows = repository.getStatistikaPoMesecimaRaw(aranzmanId, godina);

        return rows.stream()
                .map(row -> new ProdajaAranzmanaStatistikaDTO(
                        ((Number) row.get("mesec")).intValue(),
                        ((Number) row.get("broj_rezervacija")).longValue(),
                        ((Number) row.get("ukupan_prihod")).doubleValue()
                ))
                .toList();
    }
}
