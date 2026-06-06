package com.example.analiza_prodaje.controller;

import com.example.analiza_prodaje.model.ArrangementTermRevenue;
import com.example.analiza_prodaje.service.ArrangementTermRevenueService;
import org.springframework.web.bind.annotation.*;
import com.example.analiza_prodaje.dto.TermRevenueDto;
import com.example.analiza_prodaje.dto.TermPeopleDto;

import java.util.List;

@RestController
@RequestMapping("/api/arrangement-term-revenue")
@CrossOrigin(origins = "http://localhost:4200")
public class ArrangementTermRevenueController {

    private final ArrangementTermRevenueService service;

    public ArrangementTermRevenueController(ArrangementTermRevenueService service) {
        this.service = service;
    }

    @PostMapping
    public ArrangementTermRevenue create(@RequestBody ArrangementTermRevenue revenue) {
        return service.create(revenue);
    }

    @GetMapping("/{arrangementId}")
    public List<ArrangementTermRevenue> getByArrangementId(@PathVariable Long arrangementId) {
        return service.getByArrangementId(arrangementId);
    }

    @GetMapping("/{arrangementId}/{termId}/{reservationId}")
    public ArrangementTermRevenue getOne(
            @PathVariable Long arrangementId,
            @PathVariable Long termId,
            @PathVariable Long reservationId
    ) {
        return service.getOne(arrangementId, termId, reservationId);
    }

    @PatchMapping("/{arrangementId}/{termId}/{reservationId}")
    public ArrangementTermRevenue update(
            @PathVariable Long arrangementId,
            @PathVariable Long termId,
            @PathVariable Long reservationId,
            @RequestBody ArrangementTermRevenue updatedRevenue
    ) {
        return service.update(arrangementId, termId, reservationId, updatedRevenue);
    }

    @DeleteMapping("/{arrangementId}/{termId}/{reservationId}")
    public void delete(
            @PathVariable Long arrangementId,
            @PathVariable Long termId,
            @PathVariable Long reservationId
    ) {
        service.delete(arrangementId, termId, reservationId);
    }

    @GetMapping("/{arrangementId}/revenue-by-term")
    public List<TermRevenueDto> getRevenueByArrangement(
            @PathVariable Long arrangementId
    ) {
        return service.getRevenueByArrangement(arrangementId);
    }


    @GetMapping("/{arrangementId}/people-by-term")
    public List<TermPeopleDto> getTotalPeopleByTerm(
            @PathVariable Long arrangementId
    ) {
        return service.getTotalPeopleByTerm(arrangementId);
    }
}