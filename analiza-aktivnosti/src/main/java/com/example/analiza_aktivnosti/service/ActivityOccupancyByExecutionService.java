package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.ActivityOccupancyByExecution;
import com.example.analiza_aktivnosti.repository.ActivityOccupancyByExecutionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityOccupancyByExecutionService {

    private final ActivityOccupancyByExecutionRepository repository;

    public ActivityOccupancyByExecutionService(ActivityOccupancyByExecutionRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "activityOccupancy", key = "#occupancy.activityId")
    public ActivityOccupancyByExecution create(ActivityOccupancyByExecution occupancy) {
        validateForCreate(occupancy);

        occupancy.setOccupancyPercent(
                calculateOccupancyPercent(
                        occupancy.getCapacity(),
                        occupancy.getReservedSpots()
                )
        );

        return repository.save(occupancy);
    }

    public List<ActivityOccupancyByExecution> getAll() {
        return repository.findAll();
    }

    public List<ActivityOccupancyByExecution> getByActivity(Long activityId) {
        if (activityId == null) {
            throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        }

        return repository.findByActivityId(activityId);
    }

    @CacheEvict(value = "activityOccupancy", key = "#activityId")
    public ActivityOccupancyByExecution update(
            Long activityId,
            Long executionId,
            ActivityOccupancyByExecution updated
    ) {
        if (activityId == null || executionId == null) {
            throw new IllegalArgumentException("ID aktivnosti i ID izvršenja su obavezni.");
        }

        ActivityOccupancyByExecution existing = repository.findOne(activityId, executionId);

        if (existing == null) {
            return null;
        }

        if (updated.getActivityName() != null) {
            existing.setActivityName(updated.getActivityName());
        }

        if (updated.getCapacity() != null) {
            if (updated.getCapacity() <= 0) {
                throw new IllegalArgumentException("Kapacitet mora biti veći od 0.");
            }

            existing.setCapacity(updated.getCapacity());
        }

        if (updated.getReservedSpots() != null) {
            if (updated.getReservedSpots() < 0) {
                throw new IllegalArgumentException("Broj rezervisanih mesta ne sme biti negativan.");
            }

            existing.setReservedSpots(updated.getReservedSpots());
        }

        if (updated.getStatus() != null) {
            existing.setStatus(updated.getStatus());
        }

        if (existing.getReservedSpots() > existing.getCapacity()) {
            throw new IllegalArgumentException(
                    "Broj rezervisanih mesta ne može biti veći od kapaciteta."
            );
        }

        existing.setOccupancyPercent(
                calculateOccupancyPercent(
                        existing.getCapacity(),
                        existing.getReservedSpots()
                )
        );

        return repository.save(existing);
    }

    @CacheEvict(value = "activityOccupancy", key = "#activityId")
    public boolean delete(Long activityId, Long executionId) {

        if (activityId == null || executionId == null) {
            throw new IllegalArgumentException(
                    "ID aktivnosti i ID izvršenja su obavezni."
            );
        }

        ActivityOccupancyByExecution existing =
                repository.findOne(activityId, executionId);

        if (existing == null) {
            return false;
        }

        repository.deleteOne(activityId, executionId);

        return true;
    }

    @Cacheable(value = "activityOccupancy", key = "#activityId")
    public Double averageOccupancyByActivity(Long activityId) {

        if (activityId == null) {
            throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        }

        Double result = repository.averageOccupancyByActivity(activityId);

        return result == null ? 0.0 : result;
    }

    private void validateForCreate(ActivityOccupancyByExecution occupancy) {

        if (occupancy == null) {
            throw new IllegalArgumentException(
                    "Podatak o popunjenosti ne sme biti prazan."
            );
        }

        if (occupancy.getActivityId() == null) {
            throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        }

        if (occupancy.getExecutionId() == null) {
            throw new IllegalArgumentException(
                    "ID izvršenja aktivnosti je obavezan."
            );
        }

        if (occupancy.getActivityName() == null
                || occupancy.getActivityName().isBlank()) {

            throw new IllegalArgumentException(
                    "Naziv aktivnosti je obavezan."
            );
        }

        if (occupancy.getCapacity() == null
                || occupancy.getCapacity() <= 0) {

            throw new IllegalArgumentException(
                    "Kapacitet mora biti veći od 0."
            );
        }

        if (occupancy.getReservedSpots() == null
                || occupancy.getReservedSpots() < 0) {

            throw new IllegalArgumentException(
                    "Broj rezervisanih mesta ne sme biti negativan."
            );
        }

        if (occupancy.getReservedSpots() > occupancy.getCapacity()) {

            throw new IllegalArgumentException(
                    "Broj rezervisanih mesta ne može biti veći od kapaciteta."
            );
        }

        if (occupancy.getStatus() == null
                || occupancy.getStatus().isBlank()) {

            throw new IllegalArgumentException("Status je obavezan.");
        }
    }

    private Double calculateOccupancyPercent(
            Integer capacity,
            Integer reservedSpots
    ) {

        return Math.round(
                (reservedSpots * 10000.0) / capacity
        ) / 100.0;
    }
}