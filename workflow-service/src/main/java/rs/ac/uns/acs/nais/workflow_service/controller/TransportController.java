package rs.ac.uns.acs.nais.workflow_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.acs.nais.workflow_service.dto.TransportDTO;
import rs.ac.uns.acs.nais.workflow_service.service.ITransportService;

import java.util.List;

@RestController
@RequestMapping("/api/transports")
public class TransportController {

    private final ITransportService transportService;

    public TransportController(ITransportService transportService) {
        this.transportService = transportService;
    }

    @GetMapping
    public ResponseEntity<List<TransportDTO>> getAll() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transportService.getTransportById(id));
    }

    @PostMapping
    public ResponseEntity<TransportDTO> create(@RequestBody TransportDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transportService.createTransport(dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TransportDTO> update(@PathVariable Long id,
                                               @RequestBody TransportDTO dto) {
        return ResponseEntity.ok(transportService.updateTransport(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transportService.deleteTransport(id);
        return ResponseEntity.noContent().build();
    }
}