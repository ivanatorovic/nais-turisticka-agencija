package com.example.analiza_aktivnosti.controller;

import com.example.analiza_aktivnosti.dto.ArrangementRevenueDto;
import com.example.analiza_aktivnosti.entity.ArrangementActivityRevenue;
import com.example.analiza_aktivnosti.service.ArrangementActivityRevenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arrangement-activity-revenue")
public class ArrangementActivityRevenueController {

    private final ArrangementActivityRevenueService service;

    public ArrangementActivityRevenueController(
            ArrangementActivityRevenueService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody ArrangementActivityRevenue revenue
    ) {

        return new ResponseEntity<>(
                service.create(revenue),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    @GetMapping("/{arrangementId}")
    public ResponseEntity<?> getByArrangement(
            @PathVariable Long arrangementId
    ) {

        return ResponseEntity.ok(
                service.getByArrangement(arrangementId)
        );
    }

    @PutMapping("/{arrangementId}/{activityId}/{registrationId}")
    public ResponseEntity<?> update(
            @PathVariable Long arrangementId,
            @PathVariable Long activityId,
            @PathVariable Long registrationId,
            @RequestBody ArrangementActivityRevenue updated
    ) {

        ArrangementActivityRevenue result =
                service.update(
                        arrangementId,
                        activityId,
                        registrationId,
                        updated
                );

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{arrangementId}/{activityId}/{registrationId}")
    public ResponseEntity<?> delete(
            @PathVariable Long arrangementId,
            @PathVariable Long activityId,
            @PathVariable Long registrationId
    ) {

        boolean deleted =
                service.delete(
                        arrangementId,
                        activityId,
                        registrationId
                );

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics/{arrangementId}")
    public ResponseEntity<List<ArrangementRevenueDto>>
    getRevenueStatistics(
            @PathVariable Long arrangementId
    ) {

        return ResponseEntity.ok(
                service.getRevenueStatistics(arrangementId)
        );
    }
}