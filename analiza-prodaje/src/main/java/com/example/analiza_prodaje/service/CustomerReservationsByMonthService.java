package com.example.analiza_prodaje.service;

import com.example.analiza_prodaje.dto.CustomerReservationsByMonthDto;
import com.example.analiza_prodaje.dto.CustomerRevenueByMonthDto;
import com.example.analiza_prodaje.model.CustomerReservationsByMonth;
import com.example.analiza_prodaje.repository.CustomerReservationsByMonthRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerReservationsByMonthService {

    private final CustomerReservationsByMonthRepository repository;

    public CustomerReservationsByMonthService(CustomerReservationsByMonthRepository repository) {
        this.repository = repository;
    }

    @Caching(evict = {
            @CacheEvict(value = "customerRevenueInMonth", key = "#dto.customerId + '_' + #dto.month"),
            @CacheEvict(value = "customerRevenueByMonth", key = "#dto.customerId")
    })
    public CustomerReservationsByMonth create(CustomerReservationsByMonthDto dto) {
        CustomerReservationsByMonth reservation = mapToEntity(dto);
        return repository.save(reservation);
    }

    public List<CustomerReservationsByMonth> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    public List<CustomerReservationsByMonth> findByCustomerIdAndMonth(
            Long customerId,
            String month
    ) {
        return repository.findByCustomerIdAndMonth(customerId, month);
    }

    public CustomerReservationsByMonth findOne(
            Long customerId,
            String month,
            Long reservationId
    ) {
        return repository.findOne(customerId, month, reservationId);
    }

    @Caching(evict = {
            @CacheEvict(value = "customerRevenueInMonth", key = "#customerId + '_' + #month"),
            @CacheEvict(value = "customerRevenueByMonth", key = "#customerId")
    })
    public CustomerReservationsByMonth patch(
            Long customerId,
            String month,
            Long reservationId,
            CustomerReservationsByMonthDto dto
    ) {
        CustomerReservationsByMonth existing =
                repository.findOne(customerId, month, reservationId);

        if (existing == null) {
            throw new RuntimeException("Rezervacija nije pronađena.");
        }

        if (dto.getCustomerName() != null) {
            existing.setCustomerName(dto.getCustomerName());
        }

        if (dto.getArrangementId() != null) {
            existing.setArrangementId(dto.getArrangementId());
        }

        if (dto.getArrangementName() != null) {
            existing.setArrangementName(dto.getArrangementName());
        }

        if (dto.getDestination() != null) {
            existing.setDestination(dto.getDestination());
        }

        if (dto.getReservationDate() != null) {
            existing.setReservationDate(dto.getReservationDate());
        }

        if (dto.getNumberOfPeople() != null) {
            existing.setNumberOfPeople(dto.getNumberOfPeople());
        }

        if (dto.getTotalPrice() != null) {
            existing.setTotalPrice(dto.getTotalPrice());
        }

        return repository.save(existing);
    }

    @Caching(evict = {
            @CacheEvict(value = "customerRevenueInMonth", key = "#customerId + '_' + #month"),
            @CacheEvict(value = "customerRevenueByMonth", key = "#customerId")
    })
    public CustomerReservationsByMonth update(
            Long customerId,
            String month,
            Long reservationId,
            CustomerReservationsByMonthDto dto
    ) {
        CustomerReservationsByMonth existing =
                repository.findOne(customerId, month, reservationId);

        if (existing == null) {
            throw new RuntimeException("Rezervacija nije pronađena.");
        }

        existing.setCustomerName(dto.getCustomerName());
        existing.setArrangementId(dto.getArrangementId());
        existing.setArrangementName(dto.getArrangementName());
        existing.setDestination(dto.getDestination());
        existing.setReservationDate(dto.getReservationDate());
        existing.setNumberOfPeople(dto.getNumberOfPeople());
        existing.setTotalPrice(dto.getTotalPrice());

        return repository.save(existing);
    }

    @Caching(evict = {
            @CacheEvict(value = "customerRevenueInMonth", key = "#customerId + '_' + #month"),
            @CacheEvict(value = "customerRevenueByMonth", key = "#customerId")
    })
    public void delete(Long customerId, String month, Long reservationId) {
        repository.deleteOne(customerId, month, reservationId);
    }

    @Cacheable(value = "customerRevenueInMonth", key = "#customerId + '_' + #month")
    public BigDecimal totalRevenueForCustomerInMonth(Long customerId, String month) {
        BigDecimal result = repository.totalRevenueForCustomerInMonth(customerId, month);
        return result == null ? BigDecimal.ZERO : result;
    }

    @Cacheable(value = "customerRevenueByMonth", key = "#customerId")
    public List<CustomerRevenueByMonthDto> revenueByCustomerGroupedByMonth(Long customerId) {
        return repository.revenueByCustomerGroupedByMonth(customerId)
                .stream()
                .map(r -> new CustomerRevenueByMonthDto(
                        r.getCustomerId(),
                        r.getMonth(),
                        r.getTotalPrice()
                ))
                .toList();
    }

    private CustomerReservationsByMonth mapToEntity(CustomerReservationsByMonthDto dto) {
        CustomerReservationsByMonth reservation = new CustomerReservationsByMonth();

        reservation.setCustomerId(dto.getCustomerId());
        reservation.setMonth(dto.getMonth());
        reservation.setReservationId(dto.getReservationId());
        reservation.setCustomerName(dto.getCustomerName());
        reservation.setArrangementId(dto.getArrangementId());
        reservation.setArrangementName(dto.getArrangementName());
        reservation.setDestination(dto.getDestination());
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setNumberOfPeople(dto.getNumberOfPeople());
        reservation.setTotalPrice(dto.getTotalPrice());

        return reservation;
    }
}