package com.example.analiza_aktivnosti.controller;

import com.example.analiza_aktivnosti.dto.ActivityPeopleCountDto;
import com.example.analiza_aktivnosti.entity.RegistrationByActivity;
import com.example.analiza_aktivnosti.service.ActivityRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activity-registrations")
public class ActivityRegistrationController {

    private final ActivityRegistrationService activityRegistrationService;

    public ActivityRegistrationController(ActivityRegistrationService activityRegistrationService) {
        this.activityRegistrationService = activityRegistrationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationByActivity> createRegistration(@RequestBody RegistrationByActivity registration) {
        return new ResponseEntity<>(activityRegistrationService.createRegistration(registration), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RegistrationByActivity>> getAllRegistrations() {
        return ResponseEntity.ok(activityRegistrationService.getAllRegistrations());
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<RegistrationByActivity>> getRegistrationsByActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(activityRegistrationService.getRegistrationsByActivity(activityId));
    }

    @PutMapping("/{activityId}/{registrationId}")
    public ResponseEntity<RegistrationByActivity> updateRegistration(
            @PathVariable Long activityId,
            @PathVariable Long registrationId,
            @RequestBody RegistrationByActivity updatedRegistration
    ) {
        RegistrationByActivity updated =
                activityRegistrationService.updateRegistration(activityId, registrationId, updatedRegistration);

        if (updated == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{activityId}/{registrationId}")
    public ResponseEntity<Void> deleteRegistration(
            @PathVariable Long activityId,
            @PathVariable Long registrationId
    ) {
        boolean deleted = activityRegistrationService.deleteRegistration(activityId, registrationId);

        if (!deleted) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/people-count/{activityId}")
    public ResponseEntity<ActivityPeopleCountDto> countPeopleByActivity(@PathVariable Long activityId) {
        Long totalPeople = activityRegistrationService.countPeopleByActivity(activityId);
        return ResponseEntity.ok(new ActivityPeopleCountDto(activityId, totalPeople));
    }
}