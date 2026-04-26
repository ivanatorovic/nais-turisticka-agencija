package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Facility;

public interface FacilityRepository extends Neo4jRepository<Facility, Long> {

    @Query("MATCH (f:Facility) RETURN coalesce(max(f.id), 0)")
    Long findMaxId();
}