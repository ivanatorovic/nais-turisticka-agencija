package com.example.analiza_prodaje.controller;


import com.example.analiza_prodaje.dto.ProdajaAranzmanaStatistikaDTO;
import com.example.analiza_prodaje.dto.UpdateProdajaAranzmanaDTO;
import com.example.analiza_prodaje.model.ProdajaAranzmanaPoMesecu;
import com.example.analiza_prodaje.service.ProdajaAranzmanaPoMesecuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prodaja-aranzmana-po-mesecu")
public class ProdajaAranzmanaPoMesecuController {

    @Autowired
    private ProdajaAranzmanaPoMesecuService service;

    @GetMapping
    public ResponseEntity<List<ProdajaAranzmanaPoMesecu>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<ProdajaAranzmanaPoMesecu> create(@RequestBody ProdajaAranzmanaPoMesecu prodaja) {
        return new ResponseEntity<>(service.create(prodaja), HttpStatus.CREATED);
    }

    @PatchMapping("/{aranzmanId}/{godina}/{mesec}/{rezervacijaId}")
    public ResponseEntity<ProdajaAranzmanaPoMesecu> patchUpdate(
            @PathVariable UUID aranzmanId,
            @PathVariable Integer godina,
            @PathVariable Integer mesec,
            @PathVariable UUID rezervacijaId,
            @RequestBody UpdateProdajaAranzmanaDTO dto
    ) {
        return ResponseEntity.ok(
                service.patchUpdate(aranzmanId, godina, mesec, rezervacijaId, dto)
        );
    }

    @GetMapping("/{aranzmanId}/{godina}/{mesec}/{rezervacijaId}")
    public ResponseEntity<ProdajaAranzmanaPoMesecu> findOne(
            @PathVariable UUID aranzmanId,
            @PathVariable Integer godina,
            @PathVariable Integer mesec,
            @PathVariable UUID rezervacijaId
    ) {

        return ResponseEntity.ok(
                service.findOne(
                        aranzmanId,
                        godina,
                        mesec,
                        rezervacijaId
                )
        );
    }

    @DeleteMapping("/{aranzmanId}/{godina}/{mesec}/{rezervacijaId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID aranzmanId,
            @PathVariable Integer godina,
            @PathVariable Integer mesec,
            @PathVariable UUID rezervacijaId
    ) {

        service.delete(aranzmanId, godina, mesec, rezervacijaId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{aranzmanId}/{godina}")
    public ResponseEntity<List<ProdajaAranzmanaPoMesecu>> findByAranzmanAndGodina(
            @PathVariable UUID aranzmanId,
            @PathVariable Integer godina
    ) {
        return ResponseEntity.ok(service.findByAranzmanAndGodina(aranzmanId, godina));
    }

    @GetMapping("/{aranzmanId}/{godina}/{mesec}")
    public ResponseEntity<List<ProdajaAranzmanaPoMesecu>> findByAranzmanGodinaAndMesec(
            @PathVariable UUID aranzmanId,
            @PathVariable Integer godina,
            @PathVariable Integer mesec
    ) {
        return ResponseEntity.ok(service.findByAranzmanGodinaAndMesec(aranzmanId, godina, mesec));
    }

    @GetMapping("/statistika/{aranzmanId}/{godina}")
    public ResponseEntity<List<ProdajaAranzmanaStatistikaDTO>> getStatistikaPoMesecima(
            @PathVariable UUID aranzmanId,
            @PathVariable Integer godina
    ) {
        return ResponseEntity.ok(service.getStatistikaPoMesecima(aranzmanId, godina));
    }
}
