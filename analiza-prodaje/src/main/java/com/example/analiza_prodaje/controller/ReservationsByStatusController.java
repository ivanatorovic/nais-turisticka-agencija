package com.example.analiza_prodaje.controller;

import com.example.analiza_prodaje.model.ReservationsByStatus;
import com.example.analiza_prodaje.service.ReservationsByStatusService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reservations-by-status")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationsByStatusController {

    private final ReservationsByStatusService service;

    public ReservationsByStatusController(ReservationsByStatusService service) {
        this.service = service;
    }

    @PostMapping
    public ReservationsByStatus create(@RequestBody ReservationsByStatus reservation) {
        return service.create(reservation);
    }

    @GetMapping("/{status}")
    public List<ReservationsByStatus> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @GetMapping("/{status}/date-range")
    public List<ReservationsByStatus> getByStatusAndDateRange(
            @PathVariable String status,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return service.getByStatusAndDateRange(status, from, to);
    }

    @GetMapping("/{status}/{reservationDate}/{reservationId}")
    public ReservationsByStatus getOne(
            @PathVariable String status,
            @PathVariable LocalDate reservationDate,
            @PathVariable Long reservationId
    ) {
        return service.getOne(status, reservationDate, reservationId);
    }

    @PatchMapping("/{status}/{reservationDate}/{reservationId}")
    public ReservationsByStatus update(
            @PathVariable String status,
            @PathVariable LocalDate reservationDate,
            @PathVariable Long reservationId,
            @RequestBody ReservationsByStatus updatedReservation
    ) {
        return service.update(status, reservationDate, reservationId, updatedReservation);
    }

    @DeleteMapping("/{status}/{reservationDate}/{reservationId}")
    public void delete(
            @PathVariable String status,
            @PathVariable LocalDate reservationDate,
            @PathVariable Long reservationId
    ) {
        service.delete(status, reservationDate, reservationId);
    }
}