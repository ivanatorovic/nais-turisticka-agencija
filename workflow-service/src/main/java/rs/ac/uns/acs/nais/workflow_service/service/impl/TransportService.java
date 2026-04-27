package rs.ac.uns.acs.nais.workflow_service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.ac.uns.acs.nais.workflow_service.dto.TransportDTO;
import rs.ac.uns.acs.nais.workflow_service.model.Transport;
import rs.ac.uns.acs.nais.workflow_service.repository.TransportRepository;
import rs.ac.uns.acs.nais.workflow_service.service.ITransportService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportService implements ITransportService {

    private final TransportRepository transportRepository;

    public TransportService(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Override
    public List<TransportDTO> getAllTransports() {
        return transportRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TransportDTO getTransportById(Long id) {
        Transport t = transportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Transport not found with id: " + id
                ));

        return mapToDTO(t);
    }

    @Override
    public TransportDTO createTransport(TransportDTO dto) {

        validate(dto);

        Long newId = transportRepository.findMaxId() + 1;

        Transport t = new Transport();
        t.setId(newId);
        t.setType(dto.getType());
        t.setCompany(dto.getCompany());
        t.setRating(dto.getRating());

        return mapToDTO(transportRepository.save(t));
    }

    @Override
    public TransportDTO updateTransport(Long id, TransportDTO dto) {

        Transport existing = transportRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Transport not found"
                ));

        if (dto.getId() != null && !dto.getId().equals(id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Changing transport ID is not allowed"
            );
        }

        if (dto.getRating() != null && (dto.getRating() < 1 || dto.getRating() > 5)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Rating must be between 1 and 5"
            );
        }

        if (dto.getType() != null) {
            existing.setType(dto.getType());
        }

        if (dto.getCompany() != null) {
            if (dto.getCompany().isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Company cannot be empty"
                );
            }
            existing.setCompany(dto.getCompany());
        }

        if (dto.getRating() != null) {
            existing.setRating(dto.getRating());
        }

        return mapToDTO(transportRepository.save(existing));
    }

    @Override
    public void deleteTransport(Long id) {
        if (!transportRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transport not found"
            );
        }

        transportRepository.deleteById(id);
    }

    private void validate(TransportDTO dto) {

        if (dto.getRating() != null && (dto.getRating() < 1 || dto.getRating() > 5)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Rating must be between 1 and 5"
            );
        }

        if (dto.getCompany() == null || dto.getCompany().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Company is required"
            );
        }
    }

    private TransportDTO mapToDTO(Transport t) {
        return new TransportDTO(
                t.getId(),
                t.getType(),
                t.getCompany(),
                t.getRating()
        );
    }
}