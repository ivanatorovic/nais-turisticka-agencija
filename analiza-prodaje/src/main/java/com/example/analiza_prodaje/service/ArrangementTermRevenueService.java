package com.example.analiza_prodaje.service;

import com.example.analiza_prodaje.model.ArrangementTermRevenue;
import com.example.analiza_prodaje.repository.ArrangementTermRevenueRepository;
import org.springframework.stereotype.Service;
import com.example.analiza_prodaje.dto.TermRevenueDto;
import com.example.analiza_prodaje.dto.TermPeopleDto;

import java.util.List;

@Service
public class ArrangementTermRevenueService {

    private final ArrangementTermRevenueRepository repository;

    public ArrangementTermRevenueService(ArrangementTermRevenueRepository repository) {
        this.repository = repository;
    }

    public ArrangementTermRevenue create(ArrangementTermRevenue revenue) {
        return repository.save(revenue);
    }

    public List<ArrangementTermRevenue> getByArrangementId(Long arrangementId) {
        return repository.findByArrangementId(arrangementId);
    }

    public ArrangementTermRevenue getOne(Long arrangementId, Long termId, Long reservationId) {
        return repository.findOne(arrangementId, termId, reservationId);
    }

    public ArrangementTermRevenue update(
            Long arrangementId,
            Long termId,
            Long reservationId,
            ArrangementTermRevenue updatedRevenue
    ) {
        ArrangementTermRevenue existingRevenue =
                repository.findOne(arrangementId, termId, reservationId);

        if (existingRevenue == null) {
            return null;
        }

        if (updatedRevenue.getArrangementName() != null) {
            existingRevenue.setArrangementName(updatedRevenue.getArrangementName());
        }

        if (updatedRevenue.getTermStartDate() != null) {
            existingRevenue.setTermStartDate(updatedRevenue.getTermStartDate());
        }

        if (updatedRevenue.getTermEndDate() != null) {
            existingRevenue.setTermEndDate(updatedRevenue.getTermEndDate());
        }

        if (updatedRevenue.getCustomerId() != null) {
            existingRevenue.setCustomerId(updatedRevenue.getCustomerId());
        }

        if (updatedRevenue.getCustomerName() != null) {
            existingRevenue.setCustomerName(updatedRevenue.getCustomerName());
        }

        if (updatedRevenue.getNumberOfPeople() != null) {
            existingRevenue.setNumberOfPeople(updatedRevenue.getNumberOfPeople());
        }

        if (updatedRevenue.getTotalPrice() != null) {
            existingRevenue.setTotalPrice(updatedRevenue.getTotalPrice());
        }

        return repository.save(existingRevenue);
    }

    public void delete(Long arrangementId, Long termId, Long reservationId) {
        repository.deleteOne(arrangementId, termId, reservationId);
    }

    public List<TermRevenueDto> getRevenueByArrangement(Long arrangementId) {

        return repository.revenueByArrangement(arrangementId)
                .stream()
                .map(revenue -> new TermRevenueDto(
                        revenue.getArrangementId(),
                        revenue.getTermId(),
                        revenue.getTotalPrice()
                ))
                .toList();
    }

    public List<TermPeopleDto> getTotalPeopleByTerm(Long arrangementId) {

        return repository.totalPeopleByTerm(arrangementId)
                .stream()
                .map(item -> new TermPeopleDto(
                        item.getArrangementId(),
                        item.getTermId(),
                        item.getNumberOfPeople()
                ))
                .toList();
    }
}