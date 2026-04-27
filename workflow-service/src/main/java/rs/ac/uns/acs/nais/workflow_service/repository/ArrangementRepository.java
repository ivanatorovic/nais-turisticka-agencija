package rs.ac.uns.acs.nais.workflow_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.acs.nais.workflow_service.dto.*;
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
    RETURN w.id AS id,
           w.name AS name
    """)
    WorkflowDTO getWorkflowForArrangement(Long arrangementId);

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
    RETURN o.id AS id,
           o.startDate AS startDate,
           o.endDate AS endDate,
           o.priceForChildren AS priceForChildren,
           o.priceForAdults AS priceForAdults
    """)
    List<OfferDTO> getOffersForArrangement(Long arrangementId);

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

    @Query("""
    MATCH (acc:Accommodation)-[:HAS_FACILITY]->(f:Facility)
    WHERE acc.type = "HOTEL"
    
    WITH acc,
         count(DISTINCT f) AS numberOfFacilities,
         collect(DISTINCT f.name) AS facilities
    
    RETURN acc.id AS accommodationId,
           acc.name AS hotel,
           acc.rating AS rating,
           numberOfFacilities AS numberOfFacilities,
           facilities AS facilities
    ORDER BY numberOfFacilities DESC, rating DESC
    """)
    List<HotelFacilitiesDTO> getHotelsWithFacilities();

    @Query("""
    MATCH (admin:User)-[:CREATES]->(w:Workflow)<-[:BASED_ON]-(a1:Arrangement)
          -[:HAS_OFFER]->(o1:Offer)-[:HAS_TRANSPORT]->(t:Transport)
    WHERE admin.id = $adminId

    WITH admin, a1, t

    MATCH (a2:Arrangement)-[:HAS_OFFER]->(o2:Offer)-[:HAS_TRANSPORT]->(t)
    WHERE a2.id <> a1.id

    WITH admin, a1, t, collect(DISTINCT a2.name) AS arrangementsWithSameTransport

    WHERE size(arrangementsWithSameTransport) > 0

    RETURN admin.firstName + " " + admin.lastName AS administrator,
           a1.name AS adminArrangement,
           t.company AS transportCompany,
           toString(t.type) AS transportType,
           arrangementsWithSameTransport AS arrangementsWithSameTransport,
           size(arrangementsWithSameTransport) AS numberOfArrangements
    ORDER BY numberOfArrangements DESC
    """)
    List<SameTransportArrangementDTO> findArrangementsWithSameTransportAsAdminArrangements(@Param("adminId") Long adminId);

    @Query("""
    MATCH (a:Arrangement)-[:HAS_OFFER]->(o:Offer)
    OPTIONAL MATCH (o)-[:HAS_ACCOMMODATION]->(acc:Accommodation)
    OPTIONAL MATCH (o)-[:HAS_TRANSPORT]->(t:Transport)
    WHERE acc.rating IS NOT NULL OR t.rating IS NOT NULL

    WITH a, o,
         CASE
             WHEN acc.rating IS NOT NULL AND t.rating IS NOT NULL
             THEN (toFloat(acc.rating) + toFloat(t.rating)) / 2.0
             WHEN acc.rating IS NOT NULL
             THEN toFloat(acc.rating)
             ELSE toFloat(t.rating)
         END AS averageRating,
         CASE
             WHEN acc.rating IS NOT NULL AND t.rating IS NOT NULL THEN 2
             ELSE 1
         END AS numberOfRatings

    RETURN a.id AS arrangementId,
           a.name AS arrangement,
           o.id AS offerId,
           round(averageRating * 100) / 100.0 AS averageRating,
           numberOfRatings AS numberOfRatings
    ORDER BY arrangement, offerId
    """)
    List<OfferAverageRatingDTO> getOfferAverageRatings();

    @Query("""
    MATCH (admin:User)-[:CREATES]->(w:Workflow)<-[:BASED_ON]-(a:Arrangement)
    WITH admin, w, count(DISTINCT a) AS numberOfArrangements
    WHERE numberOfArrangements > 0
    RETURN admin.id AS adminId,
           admin.firstName + " " + admin.lastName AS administrator,
           w.id AS workflowId,
           w.name AS workflowName,
           numberOfArrangements AS numberOfArrangements
    ORDER BY numberOfArrangements DESC
    """)
    List<AdminWorkflowArrangementCountDTO> getArrangementCountByAdminWorkflow();


    @Query("""
    MATCH (admin:User)-[:CREATES]->(w:Workflow)<-[:BASED_ON]-(a:Arrangement)
          -[:HAS_OFFER]->(o1:Offer)-[:HAS_ACCOMMODATION]->(acc1:Accommodation)
    WHERE admin.id = $adminId
      AND acc1.rating IS NOT NULL

    WITH admin, a, o1, acc1

    MATCH (a)-[:HAS_OFFER]->(o2:Offer)-[:HAS_ACCOMMODATION]->(acc2:Accommodation)
    WHERE o2.id <> o1.id
      AND acc2.type = acc1.type
      AND acc2.rating > acc1.rating

    RETURN admin.firstName + " " + admin.lastName AS administrator,
           a.name AS arrangement,
           o1.id AS originalOfferId,
           acc1.name AS originalAccommodation,
           acc1.rating AS originalRating,
           o2.id AS betterOfferId,
           acc2.name AS betterAccommodation,
           acc2.rating AS betterRating
    ORDER BY arrangement, originalOfferId, betterRating DESC
    """)
    List<BetterAccommodationOfferDTO> findBetterAccommodationOffers(@Param("adminId") Long adminId);

    @Query("""
    MATCH (admin:User)-[:CREATES]->(:Workflow)<-[:BASED_ON]-(a:Arrangement)
          -[:HAS_OFFER]->(:Offer)-[:HAS_ACCOMMODATION]->(acc:Accommodation)
          -[:HAS_FACILITY]->(f:Facility)
    WHERE admin.id = $adminId
      AND acc.type = "HOTEL"
      AND f.name IN ["BB", "HB", "AI"]
    
    WITH acc,
         collect(DISTINCT f.name) AS mealFacilities,
         collect(DISTINCT a.name) AS arrangements
    
    RETURN acc.id AS accommodationId,
           acc.name AS hotel,
           acc.rating AS rating,
           mealFacilities AS mealFacilities,
           arrangements AS arrangements
    ORDER BY rating DESC
    """)
    List<HotelMealDTO> getAdminHotelsWithMealOptions(@Param("adminId") Long adminId);
}