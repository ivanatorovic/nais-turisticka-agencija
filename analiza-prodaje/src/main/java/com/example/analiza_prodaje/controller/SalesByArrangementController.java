package com.example.analiza_prodaje.controller;

import com.example.analiza_prodaje.model.SalesByArrangement;
import com.example.analiza_prodaje.service.SalesByArrangementService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/sales-by-arrangement")
@CrossOrigin(origins = "http://localhost:4200")
public class SalesByArrangementController {

    private final SalesByArrangementService service;

    public SalesByArrangementController(SalesByArrangementService service) {
        this.service = service;
    }

    @PostMapping
    public SalesByArrangement create(@RequestBody SalesByArrangement sale) {
        return service.create(sale);
    }

    @GetMapping("/{arrangementId}")
    public List<SalesByArrangement> getByArrangementId(@PathVariable Long arrangementId) {
        return service.getByArrangementId(arrangementId);
    }

    @GetMapping("/{arrangementId}/{reservationId}")
    public SalesByArrangement getOne(
            @PathVariable Long arrangementId,
            @PathVariable Long reservationId
    ) {
        return service.getOne(arrangementId, reservationId);
    }

    @PatchMapping("/{arrangementId}/{reservationId}")
    public SalesByArrangement update(
            @PathVariable Long arrangementId,
            @PathVariable Long reservationId,
            @RequestBody SalesByArrangement updatedSale
    ) {
        return service.update(arrangementId, reservationId, updatedSale);
    }

    @DeleteMapping("/{arrangementId}/{reservationId}")
    public void delete(
            @PathVariable Long arrangementId,
            @PathVariable Long reservationId
    ) {
        service.delete(arrangementId, reservationId);
    }

    @GetMapping("/{arrangementId}/revenue")
    public BigDecimal getTotalRevenue(@PathVariable Long arrangementId) {
        return service.getTotalRevenue(arrangementId);
    }
}