package com.example.analiza_prodaje.service;

import com.example.analiza_prodaje.dto.DestinationRevenueDto;
import com.example.analiza_prodaje.model.DestinationSalesByMonth;
import com.example.analiza_prodaje.repository.DestinationSalesByMonthRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationSalesByMonthService {

    private final DestinationSalesByMonthRepository repository;

    public DestinationSalesByMonthService(DestinationSalesByMonthRepository repository) {
        this.repository = repository;
    }

    public DestinationSalesByMonth create(DestinationSalesByMonth sale) {
        return repository.save(sale);
    }

    public List<DestinationSalesByMonth> getByMonth(String month) {
        return repository.findByMonth(month);
    }

    public DestinationSalesByMonth getOne(String month, String destination, Long reservationId) {
        return repository.findOne(month, destination, reservationId);
    }

    public DestinationSalesByMonth update(
            String month,
            String destination,
            Long reservationId,
            DestinationSalesByMonth updated
    ) {
        DestinationSalesByMonth existing = repository.findOne(month, destination, reservationId);

        if (existing == null) {
            return null;
        }

        if (updated.getArrangementId() != null) {
            existing.setArrangementId(updated.getArrangementId());
        }

        if (updated.getArrangementName() != null) {
            existing.setArrangementName(updated.getArrangementName());
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

    public void delete(String month, String destination, Long reservationId) {
        repository.deleteOne(month, destination, reservationId);
    }

    public List<DestinationRevenueDto> getRevenueByDestination(String month) {
        return repository.revenueByDestination(month)
                .stream()
                .map(item -> new DestinationRevenueDto(
                        item.getDestination(),
                        item.getTotalPrice()
                ))
                .toList();
    }
}