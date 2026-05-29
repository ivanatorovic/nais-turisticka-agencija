package com.example.analiza_aktivnosti.controller;

import com.example.analiza_aktivnosti.entity.RegistrationByStatus;
import com.example.analiza_aktivnosti.service.RegistrationByStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/registrations-by-status")
public class RegistrationByStatusController {

    private final RegistrationByStatusService service;

    public RegistrationByStatusController(
            RegistrationByStatusService service
    ) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody RegistrationByStatus registration
    ) {

        return new ResponseEntity<>(
                service.create(registration),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getByStatus(
            @PathVariable String status
    ) {

        return ResponseEntity.ok(
                service.getByStatus(status)
        );
    }

    @GetMapping("/cancelled/date-range")
    public ResponseEntity<?> getCancelledRegistrationsBetween(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {

        return ResponseEntity.ok(
                service.getCancelledRegistrationsBetween(from, to)
        );
    }

    @PutMapping("/{status}/{registrationDate}/{registrationId}")
    public ResponseEntity<?> update(
            @PathVariable String status,
            @PathVariable LocalDate registrationDate,
            @PathVariable Long registrationId,
            @RequestBody RegistrationByStatus updated
    ) {

        RegistrationByStatus result =
                service.update(status, registrationDate,registrationId, updated);

        if (result == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{status}/{registrationDate}/{registrationId}")
    public ResponseEntity<?> delete(
            @PathVariable String status,
            @PathVariable LocalDate registrationDate,
            @PathVariable Long registrationId
    ) {

        boolean deleted =
                service.delete(status, registrationDate,registrationId);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}