package com.example.analiza_prodaje.service;

import com.example.analiza_prodaje.model.SalesByArrangement;
import com.example.analiza_prodaje.repository.SalesByArrangementRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import com.example.analiza_prodaje.messaging.event.BookingCreatedEvent;
import java.time.LocalDateTime;
@Service
public class SalesByArrangementService {

    private final SalesByArrangementRepository repository;

    public SalesByArrangementService(SalesByArrangementRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "salesByArrangementRevenue", key = "#sale.arrangementId")
    public SalesByArrangement create(SalesByArrangement sale) {
        return repository.save(sale);
    }

    public List<SalesByArrangement> getByArrangementId(Long arrangementId) {
        return repository.findByArrangementId(arrangementId);
    }

    public SalesByArrangement getOne(Long arrangementId, Long reservationId) {
        return repository.findOne(arrangementId, reservationId);
    }

    @CacheEvict(value = "salesByArrangementRevenue", key = "#arrangementId")
    public SalesByArrangement update(
            Long arrangementId,
            Long reservationId,
            SalesByArrangement updatedSale
    ) {
        SalesByArrangement existingSale =
                repository.findOne(arrangementId, reservationId);

        if (existingSale == null) {
            return null;
        }

        if (updatedSale.getArrangementName() != null) {
            existingSale.setArrangementName(updatedSale.getArrangementName());
        }

        if (updatedSale.getDestination() != null) {
            existingSale.setDestination(updatedSale.getDestination());
        }

        if (updatedSale.getReservationDate() != null) {
            existingSale.setReservationDate(updatedSale.getReservationDate());
        }

        if (updatedSale.getCustomerId() != null) {
            existingSale.setCustomerId(updatedSale.getCustomerId());
        }

        if (updatedSale.getCustomerName() != null) {
            existingSale.setCustomerName(updatedSale.getCustomerName());
        }

        if (updatedSale.getNumberOfPeople() != null) {
            existingSale.setNumberOfPeople(updatedSale.getNumberOfPeople());
        }

        if (updatedSale.getTotalPrice() != null) {
            existingSale.setTotalPrice(updatedSale.getTotalPrice());
        }

        return repository.save(existingSale);
    }

    @CacheEvict(value = "salesByArrangementRevenue", key = "#arrangementId")
    public void delete(Long arrangementId, Long reservationId) {
        repository.deleteOne(arrangementId, reservationId);
    }

    @Cacheable(value = "salesByArrangementRevenue", key = "#arrangementId")
    public BigDecimal getTotalRevenue(Long arrangementId) {
        BigDecimal total = repository.sumRevenueByArrangement(arrangementId);
        return total != null ? total : BigDecimal.ZERO;
    }


    @CacheEvict(value = "salesByArrangementRevenue", key = "#event.arrangementId")
    public SalesByArrangement createFromBookingEvent(BookingCreatedEvent event) {

        List<SalesByArrangement> existingReservations =
                repository.findByReservationId(event.getReservationId());

        if (existingReservations != null && !existingReservations.isEmpty()) {
            throw new RuntimeException(
                    "Reservation with id " + event.getReservationId() + " already exists."
            );
        }

        SalesByArrangement sale = new SalesByArrangement();

        sale.setArrangementId(event.getArrangementId());
        sale.setReservationId(event.getReservationId());
        sale.setArrangementName(event.getArrangementName());
        sale.setDestination(event.getDestination());
        sale.setReservationDate(LocalDateTime.now());
        sale.setCustomerId(event.getUserId());
        sale.setCustomerName(event.getCustomerName());
        sale.setNumberOfPeople(event.getPersons());
        sale.setTotalPrice(BigDecimal.valueOf(event.getTotalPrice()));

        return repository.save(sale);
    }
}