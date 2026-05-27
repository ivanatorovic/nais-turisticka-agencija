package com.example.analiza_aktivnosti.controller;

import com.example.analiza_aktivnosti.entity.ActivityStatisticsByMonth;
import com.example.analiza_aktivnosti.service.ActivityStatisticsByMonthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity-statistics")
public class ActivityStatisticsByMonthController {

    private final ActivityStatisticsByMonthService service;

    public ActivityStatisticsByMonthController(
            ActivityStatisticsByMonthService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ActivityStatisticsByMonth> create(
            @RequestBody ActivityStatisticsByMonth statistics
    ) {

        return new ResponseEntity<>(
                service.create(statistics),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<ActivityStatisticsByMonth>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    @GetMapping("/month/{month}")
    public ResponseEntity<List<ActivityStatisticsByMonth>> getByMonth(
            @PathVariable String month
    ) {

        return ResponseEntity.ok(
                service.getByMonth(month)
        );
    }

    @PutMapping("/{month}/{activityId}")
    public ResponseEntity<ActivityStatisticsByMonth> update(
            @PathVariable String month,
            @PathVariable Long activityId,
            @RequestBody ActivityStatisticsByMonth updated
    ) {

        ActivityStatisticsByMonth result =
                service.update(month, activityId, updated);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{month}/{activityId}")
    public ResponseEntity<Void> delete(
            @PathVariable String month,
            @PathVariable Long activityId
    ) {

        boolean deleted =
                service.delete(month, activityId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}