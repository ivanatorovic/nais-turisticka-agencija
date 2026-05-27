package com.example.analiza_aktivnosti.controller;

import com.example.analiza_aktivnosti.dto.ActivityAverageOccupancyDto;
import com.example.analiza_aktivnosti.entity.ActivityOccupancyByExecution;
import com.example.analiza_aktivnosti.service.ActivityOccupancyByExecutionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity-occupancy")
public class ActivityOccupancyByExecutionController {

    private final ActivityOccupancyByExecutionService service;

    public ActivityOccupancyByExecutionController(ActivityOccupancyByExecutionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ActivityOccupancyByExecution occupancy) {
        return new ResponseEntity<>(service.create(occupancy), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<?> getByActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(service.getByActivity(activityId));
    }

    @PutMapping("/{activityId}/{executionId}")
    public ResponseEntity<?> update(
            @PathVariable Long activityId,
            @PathVariable Long executionId,
            @RequestBody ActivityOccupancyByExecution updated
    ) {
        ActivityOccupancyByExecution result = service.update(activityId, executionId, updated);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{activityId}/{executionId}")
    public ResponseEntity<?> delete(
            @PathVariable Long activityId,
            @PathVariable Long executionId
    ) {
        boolean deleted = service.delete(activityId, executionId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/average/{activityId}")
    public ResponseEntity<ActivityAverageOccupancyDto> averageOccupancyByActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(
                new ActivityAverageOccupancyDto(
                        activityId,
                        service.averageOccupancyByActivity(activityId)
                )
        );
    }
}