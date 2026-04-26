package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Arrangement;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.model.Workflow;

import java.util.List;

public interface ArrangementRepository extends Neo4jRepository<Arrangement, Long> {

    @Query("MATCH (a:Arrangement) RETURN coalesce(max(a.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId}), (w:Workflow {id: $workflowId})
    OPTIONAL MATCH (a)-[old:BASED_ON]->(:Workflow)
    DELETE old
    MERGE (a)-[:BASED_ON]->(w)
    RETURN a
    """)
    Arrangement createOrUpdateBasedOnRelationship(Long arrangementId, Long workflowId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[:BASED_ON]->(w:Workflow)
    RETURN w
    """)
    Workflow getWorkflowForArrangement(Long arrangementId);

    @Query("""
    MATCH (w:Workflow {id: $workflowId})<-[:BASED_ON]-(a:Arrangement)
    RETURN a
    """)
    List<Arrangement> getArrangementsByWorkflow(Long workflowId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[r:BASED_ON]->(:Workflow)
    DELETE r
    """)
    void deleteBasedOnRelationship(Long arrangementId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId}), (o:Offer {id: $offerId})
    OPTIONAL MATCH (:Arrangement)-[old:HAS_OFFER]->(o)
    DELETE old
    MERGE (a)-[:HAS_OFFER]->(o)
    RETURN a
    """)
    Arrangement addOfferToArrangement(Long arrangementId, Long offerId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[:HAS_OFFER]->(o:Offer)
    RETURN o
    """)
    List<Offer> getOffersForArrangement(Long arrangementId);

    @Query("""
    MATCH (o:Offer {id: $offerId})<-[:HAS_OFFER]-(a:Arrangement)
    RETURN a
    """)
    Arrangement getArrangementForOffer(Long offerId);

    @Query("""
    MATCH (a:Arrangement {id: $arrangementId})-[r:HAS_OFFER]->(o:Offer {id: $offerId})
    DELETE r
    """)
    void removeOfferFromArrangement(Long arrangementId, Long offerId);

}