package rs.ac.uns.acs.nais.recommendation_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.model.Arrangement;

import java.util.List;

@Repository
public interface ArrangementRepository extends Neo4jRepository<Arrangement, Long> {


    @Query("""
        MATCH (a:Arrangement {id: $arrangementId}), (t:Tag {id: $tagId})
        MERGE (a)-[r:HAS_TAG]->(t)
        RETURN a
    """)
    Arrangement addTagToArrangement(@Param("arrangementId") Long arrangementId,
                                    @Param("tagId") Long tagId);

    @Query("""
        MATCH (a:Arrangement {id: $arrangementId})-[r:HAS_TAG]->(t:Tag {id: $tagId})
        DELETE r
    """)
    void removeTagFromArrangement(@Param("arrangementId") Long arrangementId,
                                  @Param("tagId") Long tagId);

    @Query("""
        MATCH (a:Arrangement {id: $arrangementId})-[r:HAS_TAG]->(t:Tag)
        RETURN a, collect(r), collect(t)
    """)
    Arrangement getArrangementWithTags(@Param("arrangementId") Long arrangementId);




    @Query("""
        MATCH (a:Arrangement {id: $arrangementId})
                OPTIONAL MATCH (a)-[old:LOCATED_IN]->(:Destination)
                DELETE old
                WITH a
                MATCH (d:Destination {id: $destinationId})
                MERGE (a)-[:LOCATED_IN]->(d)
                RETURN a
    """)
    Arrangement setArrangementLocation(@Param("arrangementId") Long arrangementId,
                                       @Param("destinationId") Long destinationId);

    @Query("""
        MATCH (a:Arrangement {id: $arrangementId})-[r:LOCATED_IN]->(d:Destination)
        DELETE r
    """)
    void removeArrangementLocation(@Param("arrangementId") Long arrangementId);

    @Query("""
        MATCH (a:Arrangement {id: $arrangementId})-[r:LOCATED_IN]->(d:Destination)
        RETURN a, collect(r), collect(d)
    """)
    Arrangement getArrangementWithDestination(@Param("arrangementId") Long arrangementId);

    @Query("""
        MATCH (a:Arrangement {id: $id})
        DETACH DELETE a
    """)
    void deleteArrangementById(@Param("id") Long id);

    @Query("""
        MATCH (a1:Arrangement {id: $arrangementId})-[:HAS_TAG]->(t:Tag)
        MATCH (a2:Arrangement)-[:HAS_TAG]->(t)
        MATCH (a1)-[:LOCATED_IN]->(d:Destination)
        MATCH (a2)-[:LOCATED_IN]->(d)
        WHERE a1 <> a2
        WITH a2, COUNT(t) AS similarity
        RETURN a2.id AS id,
               a2.name AS name,
               a2.description AS description,
               a2.price AS price,
               a2.durationDays AS durationDays
        ORDER BY similarity DESC
        LIMIT 3
    """)
    List<ArrangementRecommendationDto> findSimilarArrangements(@Param("arrangementId") Long arrangementId);
}