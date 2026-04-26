package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Transport;

public interface TransportRepository extends Neo4jRepository<Transport, Long> {

    @Query("MATCH (t:Transport) RETURN coalesce(max(t.id), 0)")
    Long findMaxId();
}