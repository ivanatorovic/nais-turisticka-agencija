package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.RegistrationByActivity;
import com.example.analiza_aktivnosti.repository.RegistrationByActivityRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityRegistrationService {

    private final RegistrationByActivityRepository registrationRepository;

    public ActivityRegistrationService(RegistrationByActivityRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @CacheEvict(value = "activityPeopleCount", key = "#registration.activityId")
    public RegistrationByActivity createRegistration(RegistrationByActivity registration) {
        validateForCreate(registration);

        if (registration.getRegistrationDate() == null) {
            registration.setRegistrationDate(LocalDateTime.now(Clock.systemUTC()));
        }

        return registrationRepository.save(registration);
    }

    public List<RegistrationByActivity> getRegistrationsByActivity(Long activityId) {
        if (activityId == null) throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        return registrationRepository.findByActivityId(activityId);
    }

    public List<RegistrationByActivity> getAllRegistrations() {
        return registrationRepository.findAll();
    }

    @CacheEvict(value = "activityPeopleCount", key = "#activityId")
    public RegistrationByActivity updateRegistration(
            Long activityId,
            Long registrationId,
            RegistrationByActivity updatedRegistration
    ) {
        if (activityId == null || registrationId == null) {
            throw new IllegalArgumentException("Activity ID i registration ID su obavezni za izmenu.");
        }

        RegistrationByActivity existing = registrationRepository.findOne(activityId, registrationId);

        if (existing == null) return null;

        if (updatedRegistration.getRegistrationDate() != null) {
            existing.setRegistrationDate(updatedRegistration.getRegistrationDate());
        }

        if (updatedRegistration.getCustomerId() != null) {
            existing.setCustomerId(updatedRegistration.getCustomerId());
        }

        if (updatedRegistration.getCustomerName() != null) {
            existing.setCustomerName(updatedRegistration.getCustomerName());
        }

        if (updatedRegistration.getNumberOfPeople() != null) {
            if (updatedRegistration.getNumberOfPeople() <= 0) {
                throw new IllegalArgumentException("Broj ljudi mora biti veći od 0.");
            }
            existing.setNumberOfPeople(updatedRegistration.getNumberOfPeople());
        }


        return registrationRepository.save(existing);
    }

    @CacheEvict(value = "activityPeopleCount", key = "#activityId")
    public boolean deleteRegistration(Long activityId, Long registrationId) {
        if (activityId == null || registrationId == null) {
            throw new IllegalArgumentException("Activity ID i registration ID su obavezni za brisanje.");
        }

        RegistrationByActivity existing = registrationRepository.findOne(activityId, registrationId);

        if (existing == null) return false;

        registrationRepository.deleteOne(activityId, registrationId);
        return true;
    }

    @Cacheable(value = "activityPeopleCount", key = "#activityId")
    public Long countPeopleByActivity(Long activityId) {
        if (activityId == null) throw new IllegalArgumentException("ID aktivnosti je obavezan.");

        Long result = registrationRepository.countPeopleByActivity(activityId);
        return result == null ? 0L : result;
    }

    private void validateForCreate(RegistrationByActivity registration) {
        if (registration == null) throw new IllegalArgumentException("Registracija ne sme biti prazna.");
        if (registration.getActivityId() == null) throw new IllegalArgumentException("ID aktivnosti je obavezan.");
        if (registration.getRegistrationId() == null) throw new IllegalArgumentException("ID registracije je obavezan.");
        if (registration.getCustomerId() == null) throw new IllegalArgumentException("ID korisnika je obavezan.");
        if (registration.getCustomerName() == null || registration.getCustomerName().isBlank()) {
            throw new IllegalArgumentException("Ime korisnika je obavezno.");
        }
        if (registration.getNumberOfPeople() == null || registration.getNumberOfPeople() <= 0) {
            throw new IllegalArgumentException("Broj ljudi mora biti veći od 0.");
        }
    }
}