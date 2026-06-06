package com.example.analiza_prodaje.controller;

import com.example.analiza_prodaje.dto.CustomerReservationsByMonthDto;
import com.example.analiza_prodaje.dto.CustomerRevenueByMonthDto;
import com.example.analiza_prodaje.model.CustomerReservationsByMonth;
import com.example.analiza_prodaje.service.CustomerReservationsByMonthService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customer-reservations-by-month")
@CrossOrigin(origins = "*")
public class CustomerReservationsByMonthController {

    private final CustomerReservationsByMonthService service;

    public CustomerReservationsByMonthController(CustomerReservationsByMonthService service) {
        this.service = service;
    }

    @PostMapping
    public CustomerReservationsByMonth create(@RequestBody CustomerReservationsByMonthDto dto) {
        return service.create(dto);
    }

    @GetMapping("/customer/{customerId}")
    public List<CustomerReservationsByMonth> findByCustomerId(@PathVariable Long customerId) {
        return service.findByCustomerId(customerId);
    }

    @GetMapping("/customer/{customerId}/month/{month}")
    public List<CustomerReservationsByMonth> findByCustomerIdAndMonth(
            @PathVariable Long customerId,
            @PathVariable String month
    ) {
        return service.findByCustomerIdAndMonth(customerId, month);
    }

    @GetMapping("/customer/{customerId}/month/{month}/reservation/{reservationId}")
    public CustomerReservationsByMonth findOne(
            @PathVariable Long customerId,
            @PathVariable String month,
            @PathVariable Long reservationId
    ) {
        return service.findOne(customerId, month, reservationId);
    }

    @PutMapping("/customer/{customerId}/month/{month}/reservation/{reservationId}")
    public CustomerReservationsByMonth update(
            @PathVariable Long customerId,
            @PathVariable String month,
            @PathVariable Long reservationId,
            @RequestBody CustomerReservationsByMonthDto dto
    ) {
        return service.update(customerId, month, reservationId, dto);
    }

    @PatchMapping("/customer/{customerId}/month/{month}/reservation/{reservationId}")
    public CustomerReservationsByMonth patch(
            @PathVariable Long customerId,
            @PathVariable String month,
            @PathVariable Long reservationId,
            @RequestBody CustomerReservationsByMonthDto dto
    ) {
        return service.patch(customerId, month, reservationId, dto);
    }

    @DeleteMapping("/customer/{customerId}/month/{month}/reservation/{reservationId}")
    public void delete(
            @PathVariable Long customerId,
            @PathVariable String month,
            @PathVariable Long reservationId
    ) {
        service.delete(customerId, month, reservationId);
    }

    @GetMapping("/customer/{customerId}/month/{month}/revenue")
    public BigDecimal totalRevenueForCustomerInMonth(
            @PathVariable Long customerId,
            @PathVariable String month
    ) {
        return service.totalRevenueForCustomerInMonth(customerId, month);
    }

    @GetMapping("/customer/{customerId}/grouped-revenue")
    public List<CustomerRevenueByMonthDto> revenueByCustomerGroupedByMonth(
            @PathVariable Long customerId) {

        return service.revenueByCustomerGroupedByMonth(customerId);
    }
}