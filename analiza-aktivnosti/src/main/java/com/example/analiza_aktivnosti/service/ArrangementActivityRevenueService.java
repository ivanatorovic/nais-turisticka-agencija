package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.dto.ArrangementRevenueDto;
import com.example.analiza_aktivnosti.entity.ArrangementActivityRevenue;
import com.example.analiza_aktivnosti.repository.ArrangementActivityRevenueRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ArrangementActivityRevenueService {

    private final ArrangementActivityRevenueRepository repository;

    public ArrangementActivityRevenueService(
            ArrangementActivityRevenueRepository repository
    ) {
        this.repository = repository;
    }

    @CacheEvict(
            value = "arrangementRevenueStatistics",
            key = "#revenue.arrangementId"
    )
    public ArrangementActivityRevenue create(
            ArrangementActivityRevenue revenue
    ) {

        validateForCreate(revenue);

        if (revenue.getRegistrationDate() == null) {
            revenue.setRegistrationDate(
                    LocalDateTime.now(Clock.systemUTC())
            );
        }

        return repository.save(revenue);
    }

    public List<ArrangementActivityRevenue> getAll() {
        return repository.findAll();
    }

    public List<ArrangementActivityRevenue> getByArrangement(
            Long arrangementId
    ) {

        if (arrangementId == null) {
            throw new IllegalArgumentException(
                    "ID aranžmana je obavezan."
            );
        }

        return repository.findByArrangementId(arrangementId);
    }

    @CacheEvict(
            value = "arrangementRevenueStatistics",
            key = "#arrangementId"
    )
    public ArrangementActivityRevenue update(
            Long arrangementId,
            Long activityId,
            Long registrationId,
            ArrangementActivityRevenue updated
    ) {

        ArrangementActivityRevenue existing =
                repository.findOne(
                        arrangementId,
                        activityId,
                        registrationId
                );

        if (existing == null) {
            return null;
        }

        if (updated.getActivityName() != null) {
            existing.setActivityName(updated.getActivityName());
        }

        if (updated.getCustomerId() != null) {
            existing.setCustomerId(updated.getCustomerId());
        }

        if (updated.getCustomerName() != null) {
            existing.setCustomerName(updated.getCustomerName());
        }

        if (updated.getNumberOfPeople() != null) {
            existing.setNumberOfPeople(updated.getNumberOfPeople());
        }

        if (updated.getTotalPrice() != null) {
            existing.setTotalPrice(updated.getTotalPrice());
        }

        return repository.save(existing);
    }

    @CacheEvict(
            value = "arrangementRevenueStatistics",
            key = "#arrangementId"
    )
    public boolean delete(
            Long arrangementId,
            Long activityId,
            Long registrationId
    ) {

        ArrangementActivityRevenue existing =
                repository.findOne(
                        arrangementId,
                        activityId,
                        registrationId
                );

        if (existing == null) {
            return false;
        }

        repository.deleteOne(
                arrangementId,
                activityId,
                registrationId
        );

        return true;
    }

    @Cacheable(
            value = "arrangementRevenueStatistics",
            key = "#arrangementId"
    )
    public List<ArrangementRevenueDto> getRevenueStatistics(
            Long arrangementId
    ) {

        if (arrangementId == null) {
            throw new IllegalArgumentException(
                    "ID aranžmana je obavezan."
            );
        }

        List<ArrangementActivityRevenue> result =
                repository.revenueByArrangement(arrangementId);

        List<ArrangementActivityRevenue> allActivities =
                repository.findByArrangementId(arrangementId);

        return result.stream()
                .map(item -> {

                    String activityName =
                            allActivities.stream()
                                    .filter(x ->
                                            x.getActivityId()
                                                    .equals(item.getActivityId())
                                    )
                                    .findFirst()
                                    .map(ArrangementActivityRevenue::getActivityName)
                                    .orElse("Nepoznato");

                    return new ArrangementRevenueDto(
                            item.getActivityId(),
                            activityName,
                            item.getTotalPrice()
                    );
                })
                .sorted(
                        Comparator.comparing(
                                ArrangementRevenueDto::getTotalRevenue
                        ).reversed()
                )
                .toList();
    }

    private void validateForCreate(
            ArrangementActivityRevenue revenue
    ) {

        if (revenue == null) {
            throw new IllegalArgumentException(
                    "Podaci ne smeju biti prazni."
            );
        }

        if (revenue.getArrangementId() == null) {
            throw new IllegalArgumentException(
                    "ID aranžmana je obavezan."
            );
        }

        if (revenue.getActivityId() == null) {
            throw new IllegalArgumentException(
                    "ID aktivnosti je obavezan."
            );
        }

        if (revenue.getRegistrationId() == null) {
            throw new IllegalArgumentException(
                    "ID registracije je obavezan."
            );
        }
    }
}