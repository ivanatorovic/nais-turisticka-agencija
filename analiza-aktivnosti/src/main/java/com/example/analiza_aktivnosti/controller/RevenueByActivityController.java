package com.example.analiza_aktivnosti.controller;

import com.example.analiza_aktivnosti.dto.ActivityRevenueDto;
import com.example.analiza_aktivnosti.entity.RevenueByActivity;
import com.example.analiza_aktivnosti.service.RevenueByActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/activity-revenue")
public class RevenueByActivityController {

    private final RevenueByActivityService service;

    public RevenueByActivityController(RevenueByActivityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RevenueByActivity> create(@RequestBody RevenueByActivity revenue) {
        return new ResponseEntity<>(service.create(revenue), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RevenueByActivity>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<RevenueByActivity>> getByActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(service.getByActivity(activityId));
    }

    @PutMapping("/{activityId}/{registrationId}")
    public ResponseEntity<RevenueByActivity> update(
            @PathVariable Long activityId,
            @PathVariable Long registrationId,
            @RequestBody RevenueByActivity updated
    ) {
        RevenueByActivity result = service.update(activityId, registrationId, updated);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{activityId}/{registrationId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long activityId,
            @PathVariable Long registrationId
    ) {
        boolean deleted = service.delete(activityId, registrationId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total/{activityId}")
    public ResponseEntity<ActivityRevenueDto> sumRevenueByActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(
                new ActivityRevenueDto(
                        activityId,
                        service.sumRevenueByActivity(activityId)
                )
        );
    }
}