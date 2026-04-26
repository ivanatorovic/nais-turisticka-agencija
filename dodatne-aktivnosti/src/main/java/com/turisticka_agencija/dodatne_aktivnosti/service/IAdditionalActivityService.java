package com.turisticka_agencija.dodatne_aktivnosti.service;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;

import java.util.List;

public interface IAdditionalActivityService {
    List<AdditionalActivity> findAll();
    AdditionalActivity findById(Long id);
    AdditionalActivity save(AdditionalActivity activity);
    AdditionalActivity update(Long id, AdditionalActivity activity);
    void delete(Long id);

    AdditionalActivity addCategoryToActivity(Long activityId, Long categoryId);
    AdditionalActivity removeCategoryFromActivity(Long activityId);

    AdditionalActivity addArrangementToActivity(Long activityId, Long arrangementId);
    AdditionalActivity removeArrangementFromActivity(Long activityId);

    List<AdditionalActivityDTO> recommendActivitiesBySimilarCustomers(Long customerId);
    List<AdditionalActivityDTO> recommendActivitiesByCategory(Long customerId);
    List<AdditionalActivityDTO> findAffordableActivitiesForCustomer(Long customerId);
    List<AdditionalActivityDTO> findPopularActivitiesForCustomer(Long customerId);
}