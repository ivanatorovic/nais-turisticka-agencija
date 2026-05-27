package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.RevenueByActivity;
import com.example.analiza_aktivnosti.repository.RevenueByActivityRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RevenueByActivityService {

    private final RevenueByActivityRepository repository;
    private final ActivityStatisticsByMonthService statisticsService;

    public RevenueByActivityService(
            RevenueByActivityRepository repository,
            ActivityStatisticsByMonthService statisticsService
    ) {
        this.repository = repository;
        this.statisticsService = statisticsService;
    }

    @CacheEvict(value = "activityRevenue", key = "#revenue.activityId")
    public RevenueByActivity create(RevenueByActivity revenue) {
        validate(revenue);

        if (revenue.getRegistrationDate() == null) {
            revenue.setRegistrationDate(LocalDateTime.now(Clock.systemUTC()));
        }

        RevenueByActivity saved = repository.save(revenue);

        String month = extractMonth(saved.getRegistrationDate());

        statisticsService.increaseStatistics(
                month,
                saved.getActivityId(),
                saved.getActivityName(),
                saved.getTotalPrice(),
                saved.getNumberOfPeople()
        );

        return saved;
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

    @CacheEvict(value = "activityRevenue", key = "#activityId")
    public RevenueByActivity update(Long activityId, Long registrationId, RevenueByActivity updated) {
        RevenueByActivity existing = repository.findOne(activityId, registrationId);

        if (existing == null) {
            return null;
        }

        BigDecimal oldPrice = existing.getTotalPrice();
        String oldMonth = extractMonth(existing.getRegistrationDate());

        if (updated.getRegistrationDate() != null) {
            existing.setRegistrationDate(updated.getRegistrationDate());
        }

        if (updated.getCustomerId() != null) {
            existing.setCustomerId(updated.getCustomerId());
        }

        if (updated.getCustomerName() != null) {
            existing.setCustomerName(updated.getCustomerName());
        }

        if (updated.getActivityName() != null) {
            existing.setActivityName(updated.getActivityName());
        }

        if (updated.getNumberOfPeople() != null) {
            if (updated.getNumberOfPeople() <= 0) {
                throw new IllegalArgumentException("Broj ljudi mora biti veći od 0.");
            }
            existing.setNumberOfPeople(updated.getNumberOfPeople());
        }

        if (updated.getTotalPrice() != null) {
            if (updated.getTotalPrice().signum() < 0) {
                throw new IllegalArgumentException("Ukupna cena ne sme biti negativna.");
            }
            existing.setTotalPrice(updated.getTotalPrice());
        }

        RevenueByActivity saved = repository.save(existing);

        statisticsService.decreaseStatistics(oldMonth, activityId, oldPrice);
        statisticsService.increaseStatistics(
                extractMonth(saved.getRegistrationDate()),
                saved.getActivityId(),
                saved.getActivityName(),
                saved.getTotalPrice(),
                saved.getNumberOfPeople()
        );

        return saved;
    }

    @CacheEvict(value = "activityRevenue", key = "#activityId")
    public boolean delete(Long activityId, Long registrationId) {
        RevenueByActivity existing = repository.findOne(activityId, registrationId);

        if (existing == null) {
            return false;
        }

        repository.deleteOne(activityId, registrationId);

        statisticsService.decreaseStatistics(
                extractMonth(existing.getRegistrationDate()),
                existing.getActivityId(),
                existing.getTotalPrice()
        );

        return true;
    }

    @Cacheable(value = "activityRevenue", key = "#activityId")
    public BigDecimal sumRevenueByActivity(Long activityId) {
        if (activityId == null) {
            throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        }

        BigDecimal result = repository.sumRevenueByActivity(activityId);
        return result == null ? BigDecimal.ZERO : result;
    }

    private String extractMonth(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    private void validate(RevenueByActivity revenue) {
        if (revenue == null) throw new IllegalArgumentException("Podatak o zaradi ne sme biti prazan.");
        if (revenue.getActivityId() == null) throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        if (revenue.getRegistrationId() == null) throw new IllegalArgumentException("ID registracije je obavezan.");
        if (revenue.getActivityName() == null || revenue.getActivityName().isBlank()) {
            throw new IllegalArgumentException("Naziv aktivnosti je obavezan.");
        }
        if (revenue.getCustomerId() == null) throw new IllegalArgumentException("ID korisnika je obavezan.");
        if (revenue.getCustomerName() == null || revenue.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Ime korisnika je obavezan.");
        }
        if (revenue.getNumberOfPeople() == null || revenue.getNumberOfPeople() <= 0) {
            throw new IllegalArgumentException("Broj ljudi mora biti veći od 0.");
        }
        if (revenue.getTotalPrice() == null) throw new IllegalArgumentException("Ukupna cena je obavezna.");
        if (revenue.getTotalPrice().signum() < 0) {
            throw new IllegalArgumentException("Ukupna cena ne sme biti negativna.");
        }
    }
}