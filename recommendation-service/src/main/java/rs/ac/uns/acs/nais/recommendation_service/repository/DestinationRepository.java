package rs.ac.uns.acs.nais.recommendation_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.recommendation_service.model.Destination;

@Repository
public interface DestinationRepository extends Neo4jRepository<Destination, Long> {
}