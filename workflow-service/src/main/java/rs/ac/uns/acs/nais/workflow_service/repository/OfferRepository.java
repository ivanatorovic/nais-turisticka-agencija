package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;

public interface OfferRepository extends Neo4jRepository<Offer, Long> {

    @Query("MATCH (o:Offer) RETURN coalesce(max(o.id), 0)")
    Long findMaxId();
}