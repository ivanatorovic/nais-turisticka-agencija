package com.example.analiza_aktivnosti.service;

import com.example.analiza_aktivnosti.entity.RegistrationByStatus;
import com.example.analiza_aktivnosti.repository.RegistrationByStatusRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationByStatusService {

    private final RegistrationByStatusRepository repository;

    public RegistrationByStatusService(RegistrationByStatusRepository repository) {
        this.repository = repository;
    }

    public RegistrationByStatus create(RegistrationByStatus registration) {

        validateForCreate(registration);

        if (registration.getRegistrationDate() == null) {
            registration.setRegistrationDate(LocalDateTime.now(Clock.systemUTC()));
        }

        return repository.save(registration);
    }

    public List<RegistrationByStatus> getAll() {
        return repository.findAll();
    }

    public List<RegistrationByStatus> getByStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status je obavezan.");
        }

        return repository.findByStatus(status);
    }

    public List<RegistrationByStatus> getCancelledRegistrations() {
        return repository.findByStatus("CANCELLED");
    }

    public RegistrationByStatus update(
            String status,
            Long registrationId,
            RegistrationByStatus updated
    ) {

        if (status == null || status.isBlank()) {
            throw new IllegalArgumentException("Status je obavezan.");
        }

        if (registrationId == null) {
            throw new IllegalArgumentException("ID registracije je obavezan.");
        }

        RegistrationByStatus existing =
                repository.findOne(status, registrationId);

        if (existing == null) {
            return null;
        }

        if (updated.getActivityId() != null) {
            existing.setActivityId(updated.getActivityId());
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

        if (updated.getRegistrationDate() != null) {
            existing.setRegistrationDate(updated.getRegistrationDate());
        }

        if (updated.getNumberOfPeople() != null) {

            if (updated.getNumberOfPeople() <= 0) {
                throw new IllegalArgumentException(
                        "Broj ljudi mora biti veći od 0."
                );
            }

            existing.setNumberOfPeople(updated.getNumberOfPeople());
        }

        if (updated.getTotalPrice() != null) {

            if (updated.getTotalPrice().signum() < 0) {
                throw new IllegalArgumentException(
                        "Ukupna cena ne sme biti negativna."
                );
            }

            existing.setTotalPrice(updated.getTotalPrice());
        }

        return repository.save(existing);
    }

    public boolean delete(String status, Long registrationId) {

        RegistrationByStatus existing =
                repository.findOne(status, registrationId);

        if (existing == null) {
            return false;
        }

        repository.deleteOne(status, registrationId);

        return true;
    }

    private void validateForCreate(RegistrationByStatus registration) {

        if (registration == null) {
            throw new IllegalArgumentException(
                    "Registracija ne sme biti prazna."
            );
        }

        if (registration.getStatus() == null
                || registration.getStatus().isBlank()) {

            throw new IllegalArgumentException(
                    "Status je obavezan."
            );
        }

        if (registration.getRegistrationId() == null) {
            throw new IllegalArgumentException(
                    "ID registracije je obavezan."
            );
        }

        if (registration.getActivityId() == null) {
            throw new IllegalArgumentException(
                    "ID aktivnosti je obavezan."
            );
        }

        if (registration.getActivityName() == null
                || registration.getActivityName().isBlank()) {

            throw new IllegalArgumentException(
                    "Naziv aktivnosti je obavezan."
            );
        }

        if (registration.getCustomerId() == null) {
            throw new IllegalArgumentException(
                    "ID korisnika je obavezan."
            );
        }

        if (registration.getCustomerName() == null
                || registration.getCustomerName().isBlank()) {

            throw new IllegalArgumentException(
                    "Ime korisnika je obavezno."
            );
        }

        if (registration.getNumberOfPeople() == null
                || registration.getNumberOfPeople() <= 0) {

            throw new IllegalArgumentException(
                    "Broj ljudi mora biti veći od 0."
            );
        }

        if (registration.getTotalPrice() == null
                || registration.getTotalPrice().signum() < 0) {

            throw new IllegalArgumentException(
                    "Ukupna cena mora biti veća ili jednaka 0."
            );
        }
    }
}