package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import rs.ac.uns.acs.nais.workflow_service.model.Accommodation;
import rs.ac.uns.acs.nais.workflow_service.model.Offer;
import rs.ac.uns.acs.nais.workflow_service.model.Transport;

import java.util.List;

public interface OfferRepository extends Neo4jRepository<Offer, Long> {

    @Query("MATCH (o:Offer) RETURN coalesce(max(o.id), 0)")
    Long findMaxId();

    @Query("""
    MATCH (o:Offer {id: $offerId}), (a:Accommodation {id: $accommodationId})
    OPTIONAL MATCH (o)-[old:HAS_ACCOMMODATION]->(:Accommodation)
    DELETE old
    MERGE (o)-[:HAS_ACCOMMODATION]->(a)
    RETURN o
    """)
    Offer setAccommodationForOffer(Long offerId, Long accommodationId);

    @Query("""
    MATCH (o:Offer {id: $offerId})-[:HAS_ACCOMMODATION]->(a:Accommodation)
    RETURN a
    """)
    Accommodation getAccommodationForOffer(Long offerId);

    @Query("""
    MATCH (a:Accommodation {id: $accommodationId})<-[:HAS_ACCOMMODATION]-(o:Offer)
    RETURN o
    """)
    List<Offer> getOffersForAccommodation(Long accommodationId);

    @Query("""
    MATCH (o:Offer {id: $offerId})-[r:HAS_ACCOMMODATION]->(:Accommodation)
    DELETE r
    """)
    void removeAccommodationFromOffer(Long offerId);

    @Query("""
    MATCH (o:Offer {id: $offerId}), (t:Transport {id: $transportId})
    OPTIONAL MATCH (o)-[old:HAS_TRANSPORT]->(:Transport)
    DELETE old
    MERGE (o)-[:HAS_TRANSPORT]->(t)
    RETURN o
    """)
    Offer setTransportForOffer(Long offerId, Long transportId);

    @Query("""
    MATCH (o:Offer {id: $offerId})-[:HAS_TRANSPORT]->(t:Transport)
    RETURN t
    """)
    Transport getTransportForOffer(Long offerId);

    @Query("""
    MATCH (t:Transport {id: $transportId})<-[:HAS_TRANSPORT]-(o:Offer)
    RETURN o
    """)
    List<Offer> getOffersForTransport(Long transportId);

    @Query("""
    MATCH (o:Offer {id: $offerId})-[r:HAS_TRANSPORT]->(:Transport)
    DELETE r
    """)
    void removeTransportFromOffer(Long offerId);
}