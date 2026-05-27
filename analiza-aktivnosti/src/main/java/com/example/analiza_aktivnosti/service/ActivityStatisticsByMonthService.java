package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.ActivityStatisticsByMonth;
import com.example.analiza_aktivnosti.repository.ActivityStatisticsByMonthRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class ActivityStatisticsByMonthService {

    private final ActivityStatisticsByMonthRepository repository;

    public ActivityStatisticsByMonthService(ActivityStatisticsByMonthRepository repository) {
        this.repository = repository;
    }

    public ActivityStatisticsByMonth create(ActivityStatisticsByMonth statistics) {

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

    public List<ActivityStatisticsByMonth> getByMonth(String month) {

        if (month == null || month.isBlank()) {
            throw new IllegalArgumentException("Mesec je obavezan.");
        }

        return repository.findByMonth(month)
                .stream()
                .sorted(
                        Comparator.comparing(
                                ActivityStatisticsByMonth::getTotalRevenue
                        ).reversed()
                )
                .toList();
    }

    public ActivityStatisticsByMonth update(
            String month,
            Long activityId,
            ActivityStatisticsByMonth updated
    ) {

        ActivityStatisticsByMonth existing =
                repository.findOne(month, activityId);

        if (existing == null) {
            return null;
        }

        if (updated.getActivityName() != null) {
            existing.setActivityName(updated.getActivityName());
        }

        if (updated.getTotalRevenue() != null) {

            if (updated.getTotalRevenue().signum() < 0) {
                throw new IllegalArgumentException(
                        "Ukupna zarada ne sme biti negativna."
                );
            }

            existing.setTotalRevenue(updated.getTotalRevenue());
        }

        if (updated.getTotalPeople() != null) {

            if (updated.getTotalPeople() < 0) {
                throw new IllegalArgumentException(
                        "Broj ljudi ne sme biti negativan."
                );
            }

            existing.setTotalPeople(updated.getTotalPeople());
        }

        return repository.save(existing);
    }

    public boolean delete(String month, Long activityId) {

        ActivityStatisticsByMonth existing =
                repository.findOne(month, activityId);

        if (existing == null) {
            return false;
        }

        repository.deleteOne(month, activityId);

        return true;
    }

    public void increaseStatistics(
            String month,
            Long activityId,
            String activityName,
            BigDecimal revenueToAdd,
            Integer peopleToAdd
    ) {

        ActivityStatisticsByMonth existing =
                repository.findOne(month, activityId);

        if (existing == null) {

            existing = new ActivityStatisticsByMonth();

            existing.setMonth(month);
            existing.setActivityId(activityId);
            existing.setActivityName(activityName);
            existing.setTotalRevenue(BigDecimal.ZERO);
            existing.setTotalPeople(0);
        }

        existing.setTotalRevenue(
                existing.getTotalRevenue().add(revenueToAdd)
        );

        existing.setTotalPeople(
                existing.getTotalPeople() + peopleToAdd
        );

        repository.save(existing);
    }

    public void decreaseStatistics(
            String month,
            Long activityId,
            BigDecimal revenueToSubtract
    ) {

        ActivityStatisticsByMonth existing =
                repository.findOne(month, activityId);

        if (existing == null) {
            return;
        }

        existing.setTotalRevenue(
                existing.getTotalRevenue().subtract(revenueToSubtract)
        );

        repository.save(existing);
    }

    private void validate(ActivityStatisticsByMonth statistics) {

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
    }
}