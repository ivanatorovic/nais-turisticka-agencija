package com.turisticka_agencija.dodatne_aktivnosti.service.impl;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.dto.ArrangementDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import com.turisticka_agencija.dodatne_aktivnosti.model.Arrangement;
import com.turisticka_agencija.dodatne_aktivnosti.model.Category;
import com.turisticka_agencija.dodatne_aktivnosti.repository.AdditionalActivityRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.ArrangementRepository;
import com.turisticka_agencija.dodatne_aktivnosti.repository.CategoryRepository;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditionalActivityService implements IAdditionalActivityService {

    private final AdditionalActivityRepository additionalActivityRepository;
    private final CategoryRepository categoryRepository;
    private final ArrangementRepository arrangementRepository;

    @Autowired
    public AdditionalActivityService(AdditionalActivityRepository additionalActivityRepository,
                                     CategoryRepository categoryRepository,
                                     ArrangementRepository arrangementRepository) {
        this.additionalActivityRepository = additionalActivityRepository;
        this.categoryRepository = categoryRepository;
        this.arrangementRepository = arrangementRepository;
    }

    @Override
    public List<AdditionalActivity> findAll() {
        return additionalActivityRepository.findAll();
    }

    @Override
    public AdditionalActivity findById(Long id) {
        return additionalActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Additional activity not found with id: " + id));
    }

    @Override
    public AdditionalActivity save(AdditionalActivity activity) {
        return additionalActivityRepository.save(activity);
    }

    @Override
    public AdditionalActivity update(Long id, AdditionalActivity activity) {
        AdditionalActivity existingActivity = findById(id);

        if (activity.getName() != null) {
            existingActivity.setName(activity.getName());
        }

        if (activity.getDescription() != null) {
            existingActivity.setDescription(activity.getDescription());
        }

        if (activity.getPrice() != null) {
            existingActivity.setPrice(activity.getPrice());
        }

        if (activity.getLocation() != null) {
            existingActivity.setLocation(activity.getLocation());
        }

        if (activity.getStartDate() != null) {
            existingActivity.setStartDate(activity.getStartDate());
        }

        if (activity.getEndDate() != null) {
            existingActivity.setEndDate(activity.getEndDate());
        }

        if (activity.getMaxCapacity() != null) {
            existingActivity.setMaxCapacity(activity.getMaxCapacity());
        }

        if (activity.getArrangement() != null) {
            existingActivity.setArrangement(activity.getArrangement());
        }

        if (activity.getCategory() != null) {
            existingActivity.setCategory(activity.getCategory());
        }

        return additionalActivityRepository.save(existingActivity);
    }

    @Override
    public void delete(Long id) {
        additionalActivityRepository.deleteById(id);
    }

    @Override
    public AdditionalActivity addCategoryToActivity(Long activityId, Long categoryId) {
        AdditionalActivity activity = findById(activityId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        activity.setCategory(category);
        return additionalActivityRepository.save(activity);
    }

    @Override
    public AdditionalActivity removeCategoryFromActivity(Long activityId) {
        AdditionalActivity activity = findById(activityId);
        activity.setCategory(null);
        return additionalActivityRepository.save(activity);
    }

    @Override
    public AdditionalActivity addArrangementToActivity(Long activityId, Long arrangementId) {
        AdditionalActivity activity = findById(activityId);
        Arrangement arrangement = arrangementRepository.findById(arrangementId)
                .orElseThrow(() -> new RuntimeException("Arrangement not found with id: " + arrangementId));

        activity.setArrangement(arrangement);
        return additionalActivityRepository.save(activity);
    }

    @Override
    public AdditionalActivity removeArrangementFromActivity(Long activityId) {
        AdditionalActivity activity = findById(activityId);
        activity.setArrangement(null);
        return additionalActivityRepository.save(activity);
    }

    @Override
    public List<AdditionalActivityDTO> recommendActivitiesBySimilarCustomers(Long customerId) {
        return additionalActivityRepository.recommendActivitiesBySimilarCustomers(customerId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<AdditionalActivityDTO> recommendActivitiesByCategory(Long customerId) {
        return additionalActivityRepository.recommendActivitiesByCategory(customerId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<AdditionalActivityDTO> findAffordableActivitiesForCustomer(Long customerId) {
        return additionalActivityRepository.findAffordableActivitiesForCustomer(customerId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<AdditionalActivityDTO> findPopularActivitiesForCustomer(Long customerId) {
        return additionalActivityRepository.findPopularActivitiesForCustomer(customerId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    private AdditionalActivityDTO mapToDTO(AdditionalActivity activity) {
        ArrangementDTO arrangementDTO = null;

        if (activity.getArrangement() != null) {
            arrangementDTO = new ArrangementDTO(
                    activity.getArrangement().getArrangementId(),
                    activity.getArrangement().getName()
            );
        }

        return new AdditionalActivityDTO(
                activity.getActivityId(),
                activity.getName(),
                activity.getDescription(),
                activity.getPrice(),
                activity.getLocation(),
                activity.getStartDate(),
                activity.getEndDate(),
                activity.getMaxCapacity(),
                arrangementDTO
        );
    }
}