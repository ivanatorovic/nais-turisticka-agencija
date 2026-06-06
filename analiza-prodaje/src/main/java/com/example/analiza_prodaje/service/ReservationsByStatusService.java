package com.example.analiza_prodaje.service;

import com.example.analiza_prodaje.model.ReservationsByStatus;
import com.example.analiza_prodaje.repository.ReservationsByStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationsByStatusService {

    private final ReservationsByStatusRepository repository;

    public ReservationsByStatusService(ReservationsByStatusRepository repository) {
        this.repository = repository;
    }

    public ReservationsByStatus create(ReservationsByStatus reservation) {
        return repository.save(reservation);
    }

    public List<ReservationsByStatus> getByStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<ReservationsByStatus> getByStatusAndDateRange(
            String status,
            LocalDate from,
            LocalDate to
    ) {
        return repository.findByStatusAndDateRange(status, from, to);
    }

    public ReservationsByStatus getOne(
            String status,
            LocalDate reservationDate,
            Long reservationId
    ) {
        return repository.findOne(status, reservationDate, reservationId);
    }

    public ReservationsByStatus update(
            String status,
            LocalDate reservationDate,
            Long reservationId,
            ReservationsByStatus updatedReservation
    ) {
        ReservationsByStatus existingReservation =
                repository.findOne(status, reservationDate, reservationId);

        if (existingReservation == null) {
            return null;
        }

        if (updatedReservation.getArrangementId() != null) {
            existingReservation.setArrangementId(updatedReservation.getArrangementId());
        }

        if (updatedReservation.getArrangementName() != null) {
            existingReservation.setArrangementName(updatedReservation.getArrangementName());
        }

        if (updatedReservation.getDestination() != null) {
            existingReservation.setDestination(updatedReservation.getDestination());
        }

        if (updatedReservation.getCustomerId() != null) {
            existingReservation.setCustomerId(updatedReservation.getCustomerId());
        }

        if (updatedReservation.getCustomerName() != null) {
            existingReservation.setCustomerName(updatedReservation.getCustomerName());
        }

        if (updatedReservation.getNumberOfPeople() != null) {
            existingReservation.setNumberOfPeople(updatedReservation.getNumberOfPeople());
        }

        if (updatedReservation.getTotalPrice() != null) {
            existingReservation.setTotalPrice(updatedReservation.getTotalPrice());
        }

        return repository.save(existingReservation);
    }

    public void delete(
            String status,
            LocalDate reservationDate,
            Long reservationId
    ) {
        repository.deleteOne(status, reservationDate, reservationId);
    }
}