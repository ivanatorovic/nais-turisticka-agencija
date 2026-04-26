package com.turisticka_agencija.dodatne_aktivnosti.controller;

import com.turisticka_agencija.dodatne_aktivnosti.dto.AdditionalActivityDTO;
import com.turisticka_agencija.dodatne_aktivnosti.model.AdditionalActivity;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activities")
public class AdditionalActivityController {

    private final IAdditionalActivityService additionalActivityService;

    public AdditionalActivityController(IAdditionalActivityService additionalActivityService) {
        this.additionalActivityService = additionalActivityService;
    }

    @GetMapping
    public ResponseEntity<List<AdditionalActivity>> getAllActivities() {
        List<AdditionalActivity> activities = additionalActivityService.findAll();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalActivity> getActivityById(@PathVariable Long id) {
        AdditionalActivity activity = additionalActivityService.findById(id);
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdditionalActivity> createActivity(@RequestBody AdditionalActivity additionalActivity) {
        AdditionalActivity createdActivity = additionalActivityService.save(additionalActivity);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdditionalActivity> updateActivity(@PathVariable Long id,
                                                             @RequestBody AdditionalActivity additionalActivity) {
        AdditionalActivity updatedActivity = additionalActivityService.update(id, additionalActivity);
        return new ResponseEntity<>(updatedActivity, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        additionalActivityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{activityId}/category/{categoryId}")
    public ResponseEntity<AdditionalActivity> addCategoryToActivity(@PathVariable Long activityId,
                                                                    @PathVariable Long categoryId) {
        return new ResponseEntity<>(
                additionalActivityService.addCategoryToActivity(activityId, categoryId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{activityId}/category")
    public ResponseEntity<AdditionalActivity> removeCategoryFromActivity(@PathVariable Long activityId) {
        return new ResponseEntity<>(
                additionalActivityService.removeCategoryFromActivity(activityId),
                HttpStatus.OK
        );
    }

    @PostMapping("/{activityId}/arrangement/{arrangementId}")
    public ResponseEntity<AdditionalActivity> addArrangementToActivity(@PathVariable Long activityId,
                                                                       @PathVariable Long arrangementId) {
        return new ResponseEntity<>(
                additionalActivityService.addArrangementToActivity(activityId, arrangementId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{activityId}/arrangement")
    public ResponseEntity<AdditionalActivity> removeArrangementFromActivity(@PathVariable Long activityId) {
        return new ResponseEntity<>(
                additionalActivityService.removeArrangementFromActivity(activityId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/similar-customers/{customerId}")
    public ResponseEntity<List<AdditionalActivityDTO>> recommendActivitiesBySimilarCustomers(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.recommendActivitiesBySimilarCustomers(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/category/{customerId}")
    public ResponseEntity<List<AdditionalActivityDTO>> recommendActivitiesByCategory(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.recommendActivitiesByCategory(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/affordable/{customerId}")
    public ResponseEntity<List<AdditionalActivityDTO>> findAffordableActivitiesForCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.findAffordableActivitiesForCustomer(customerId),
                HttpStatus.OK
        );
    }

    @GetMapping("/recommendations/popular/{customerId}")
    public ResponseEntity<List<AdditionalActivityDTO>> findPopularActivitiesForCustomer(@PathVariable Long customerId) {
        return new ResponseEntity<>(
                additionalActivityService.findPopularActivitiesForCustomer(customerId),
                HttpStatus.OK
        );
    }
}