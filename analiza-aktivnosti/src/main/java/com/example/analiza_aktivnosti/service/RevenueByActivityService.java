package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.RevenueByActivity;
import com.example.analiza_aktivnosti.repository.RevenueByActivityRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RevenueByActivityService {

    private final RevenueByActivityRepository repository;

    public RevenueByActivityService(RevenueByActivityRepository repository) {
        this.repository = repository;
    }

    public RevenueByActivity create(RevenueByActivity revenue) {
        validate(revenue);

        if (revenue.getRegistrationDate() == null) {
            revenue.setRegistrationDate(LocalDateTime.now(Clock.systemUTC()));
        }

        return repository.save(revenue);
    }

    public List<RevenueByActivity> getAll() {
        return repository.findAll();
    }

    public List<RevenueByActivity> getByActivity(Long activityId) {
        if (activityId == null) {
            throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        }

        return repository.findByActivityId(activityId);
    }

    public RevenueByActivity update(Long activityId, Long registrationId, RevenueByActivity updated) {
        RevenueByActivity existing = repository.findOne(activityId, registrationId);

        if (existing == null) {
            return null;
        }

        if (updated.getRegistrationDate() != null) {
            existing.setRegistrationDate(updated.getRegistrationDate());
        }

        if (updated.getCustomerId() != null) {
            existing.setCustomerId(updated.getCustomerId());
        }

        if (updated.getCustomerName() != null) {
            existing.setCustomerName(updated.getCustomerName());
        }

        if (updated.getTotalPrice() != null) {
            if (updated.getTotalPrice().signum() < 0) {
                throw new IllegalArgumentException("Ukupna cena ne sme biti negativna.");
            }
            existing.setTotalPrice(updated.getTotalPrice());
        }

        return repository.save(existing);
    }

    public boolean delete(Long activityId, Long registrationId) {
        RevenueByActivity existing = repository.findOne(activityId, registrationId);

        if (existing == null) {
            return false;
        }

        repository.deleteOne(activityId, registrationId);
        return true;
    }

    public BigDecimal sumRevenueByActivity(Long activityId) {
        if (activityId == null) {
            throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        }

        BigDecimal result = repository.sumRevenueByActivity(activityId);
        return result == null ? BigDecimal.ZERO : result;
    }

    private void validate(RevenueByActivity revenue) {
        if (revenue == null) throw new IllegalArgumentException("Podatak o zaradi ne sme biti prazan.");
        if (revenue.getActivityId() == null) throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        if (revenue.getRegistrationId() == null) throw new IllegalArgumentException("ID registracije je obavezan.");
        if (revenue.getCustomerId() == null) throw new IllegalArgumentException("ID korisnika je obavezan.");
        if (revenue.getCustomerName() == null || revenue.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Ime korisnika je obavezno.");
        }
        if (revenue.getTotalPrice() == null) throw new IllegalArgumentException("Ukupna cena je obavezna.");
        if (revenue.getTotalPrice().signum() < 0) {
            throw new IllegalArgumentException("Ukupna cena ne sme biti negativna.");
        }
    }
}