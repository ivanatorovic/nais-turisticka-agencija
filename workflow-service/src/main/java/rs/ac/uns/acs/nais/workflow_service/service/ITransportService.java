package rs.ac.uns.acs.nais.workflow_service.service;

import rs.ac.uns.acs.nais.workflow_service.dto.TransportDTO;

import java.util.List;

public interface ITransportService {

    List<TransportDTO> getAllTransports();

    TransportDTO getTransportById(Long id);

    TransportDTO createTransport(TransportDTO dto);

    TransportDTO updateTransport(Long id, TransportDTO dto);

    void deleteTransport(Long id);
}