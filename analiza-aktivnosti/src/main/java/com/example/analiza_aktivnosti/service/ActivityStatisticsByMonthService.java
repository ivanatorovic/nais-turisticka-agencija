package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.ActivityStatisticsByMonth;
import com.example.analiza_aktivnosti.repository.ActivityStatisticsByMonthRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ActivityStatisticsByMonthService {

    private final ActivityStatisticsByMonthRepository repository;

    public ActivityStatisticsByMonthService(
            ActivityStatisticsByMonthRepository repository
    ) {
        this.repository = repository;
    }

    public ActivityStatisticsByMonth create(
            ActivityStatisticsByMonth statistics
    ) {

        validate(statistics);

        if (statistics.getTotalRevenue() == null) {
            statistics.setTotalRevenue(BigDecimal.ZERO);
        }

        if (statistics.getTotalPeople() == null) {
            statistics.setTotalPeople(0);
        }

        return repository.save(statistics);
    }

    public List<ActivityStatisticsByMonth> getAll() {
        return repository.findAll();
    }

    public List<ActivityStatisticsByMonth> getTop3ByMonth(
            String month
    ) {

        if (month == null || month.isBlank()) {
            throw new IllegalArgumentException(
                    "Mesec je obavezan."
            );
        }

        return repository.findTop3ByMonth(month);
    }

    public ActivityStatisticsByMonth update(
            String month,
            BigDecimal totalRevenue,
            Long activityId,
            ActivityStatisticsByMonth updated
    ) {

        ActivityStatisticsByMonth existing =
                repository.findOne(
                        month,
                        totalRevenue,
                        activityId
                );

        if (existing == null) {
            return null;
        }

        BigDecimal oldRevenue =
                existing.getTotalRevenue();

        if (updated.getActivityName() != null) {
            existing.setActivityName(
                    updated.getActivityName()
            );
        }

        if (updated.getTotalPeople() != null) {

            if (updated.getTotalPeople() < 0) {
                throw new IllegalArgumentException(
                        "Broj ljudi ne sme biti negativan."
                );
            }

            existing.setTotalPeople(
                    updated.getTotalPeople()
            );
        }

        if (updated.getTotalRevenue() != null) {

            if (updated.getTotalRevenue().signum() < 0) {
                throw new IllegalArgumentException(
                        "Ukupna zarada ne sme biti negativna."
                );
            }

            existing.setTotalRevenue(
                    updated.getTotalRevenue()
            );
        }

        repository.deleteOne(
                month,
                oldRevenue,
                activityId
        );

        return repository.save(existing);
    }

    public boolean delete(
            String month,
            BigDecimal totalRevenue,
            Long activityId
    ) {

        ActivityStatisticsByMonth existing =
                repository.findOne(
                        month,
                        totalRevenue,
                        activityId
                );

        if (existing == null) {
            return false;
        }

        repository.deleteOne(
                month,
                totalRevenue,
                activityId
        );

        return true;
    }

    private void validate(
            ActivityStatisticsByMonth statistics
    ) {

        if (statistics == null) {
            throw new IllegalArgumentException(
                    "Statistika ne sme biti prazna."
            );
        }

        if (statistics.getMonth() == null
                || statistics.getMonth().isBlank()) {

            throw new IllegalArgumentException(
                    "Mesec je obavezan."
            );
        }

        if (statistics.getActivityId() == null) {
            throw new IllegalArgumentException(
                    "ID aktivnosti je obavezan."
            );
        }

        if (statistics.getActivityName() == null
                || statistics.getActivityName().isBlank()) {

            throw new IllegalArgumentException(
                    "Naziv aktivnosti je obavezan."
            );
        }

        if (statistics.getTotalRevenue() != null
                && statistics.getTotalRevenue().signum() < 0) {

            throw new IllegalArgumentException(
                    "Ukupna zarada ne sme biti negativna."
            );
        }

        if (statistics.getTotalPeople() != null
                && statistics.getTotalPeople() < 0) {

            throw new IllegalArgumentException(
                    "Broj ljudi ne sme biti negativan."
            );
        }
    }
}