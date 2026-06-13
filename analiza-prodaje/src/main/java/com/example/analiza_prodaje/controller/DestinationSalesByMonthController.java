package com.example.analiza_prodaje.controller;

import com.example.analiza_prodaje.dto.DestinationRevenueDto;
import com.example.analiza_prodaje.model.DestinationSalesByMonth;
import com.example.analiza_prodaje.service.DestinationSalesByMonthService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destination-sales-by-month")
@CrossOrigin(origins = "http://localhost:4200")
public class DestinationSalesByMonthController {

    private final DestinationSalesByMonthService service;

    public DestinationSalesByMonthController(DestinationSalesByMonthService service) {
        this.service = service;
    }

    @PostMapping
    public DestinationSalesByMonth create(@RequestBody DestinationSalesByMonth sale) {
        return service.create(sale);
    }

    @GetMapping("/{month}")
    public List<DestinationSalesByMonth> getByMonth(@PathVariable String month) {
        return service.getByMonth(month);
    }

    @GetMapping("/{month}/{destination}/{reservationId}")
    public DestinationSalesByMonth getOne(
            @PathVariable String month,
            @PathVariable String destination,
            @PathVariable Long reservationId
    ) {
        return service.getOne(month, destination, reservationId);
    }

    @PatchMapping("/{month}/{destination}/{reservationId}")
    public DestinationSalesByMonth update(
            @PathVariable String month,
            @PathVariable String destination,
            @PathVariable Long reservationId,
            @RequestBody DestinationSalesByMonth updated
    ) {
        return service.update(month, destination, reservationId, updated);
    }

    @DeleteMapping("/{month}/{destination}/{reservationId}")
    public void delete(
            @PathVariable String month,
            @PathVariable String destination,
            @PathVariable Long reservationId
    ) {
        service.delete(month, destination, reservationId);
    }

    @GetMapping("/{month}/revenue-by-destination")
    public List<DestinationRevenueDto> getRevenueByDestination(@PathVariable String month) {
        return service.getRevenueByDestination(month);
    }
}