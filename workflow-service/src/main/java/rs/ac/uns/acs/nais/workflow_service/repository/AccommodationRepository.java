package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Accommodation;

public interface AccommodationRepository extends Neo4jRepository<Accommodation, Long> {

    @Query("MATCH (a:Accommodation) RETURN coalesce(max(a.id), 0)")
    Long findMaxId();
}