package rs.ac.uns.acs.nais.recommendation_service.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.acs.nais.recommendation_service.dto.ArrangementRecommendationDto;
import rs.ac.uns.acs.nais.recommendation_service.dto.BookedArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.dto.ViewedArrangementResponse;
import rs.ac.uns.acs.nais.recommendation_service.model.User;

import java.util.List;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("""
        MATCH (u:User {id: $userId}), (a:Arrangement {id: $arrangementId})
        MERGE (u)-[r:VIEWED]->(a)
        ON CREATE SET 
            r.viewedAt = toString(datetime()),
            r.count = 1
        ON MATCH SET 
            r.viewedAt = toString(datetime()),
            r.count = COALESCE(r.count, 0) + 1
        RETURN u
        """)
    User addOrUpdateViewed(@Param("userId") Long userId,
                           @Param("arrangementId") Long arrangementId);

    @Query("""
        MATCH (u:User {id: $userId})-[r:VIEWED]->(a:Arrangement {id: $arrangementId})
        DELETE r
    """)
    void deleteViewedRelationship(@Param("userId") Long userId,
                                  @Param("arrangementId") Long arrangementId);

    @Query("""
        MATCH (u:User {id: $userId}), (a:Arrangement {id: $arrangementId})
        MERGE (u)-[r:BOOKED]->(a)
        ON CREATE SET
            r.bookingDate = toString(datetime()),
            r.persons = $persons,
            r.totalPrice = $totalPrice,
            r.count = 1
        ON MATCH SET
            r.bookingDate = toString(datetime()),
            r.persons = $persons,
            r.totalPrice = $totalPrice,
            r.count = coalesce(r.count, 0) + 1
        RETURN u
        """)
    User addOrUpdateBooked(@Param("userId") Long userId,
                           @Param("arrangementId") Long arrangementId,
                           @Param("persons") Integer persons,
                           @Param("totalPrice") Double totalPrice);

    @Query("""
        MATCH (u:User {id: $userId})-[r:BOOKED]->(a:Arrangement {id: $arrangementId})
        DELETE r
    """)
    void deleteBookedRelationship(@Param("userId") Long userId,
                                  @Param("arrangementId") Long arrangementId);

    @Query("""
    MATCH (u:User {id: $userId})-[r:VIEWED]->(a:Arrangement)
    RETURN a.id AS arrangementId,
           a.name AS arrangementName,
           r.viewedAt AS viewedAt,
           r.count AS count
""")
    List<ViewedArrangementResponse> findUserWithViewedRelationships(@Param("userId") Long userId);

    @Query("""
            MATCH (u:User {id: $userId})-[r:BOOKED]->(a:Arrangement)
            RETURN a.id AS arrangementId,
                   a.name AS arrangementName,
                   r.bookingDate AS bookingDate,
                   r.persons AS persons,
                   r.totalPrice AS totalPrice,
                   r.count AS count
        """)
    List<BookedArrangementResponse> findUserWithBookedRelationships(@Param("userId") Long userId);


    @Query("""
            MATCH (u:User {id: $userId})-[:VIEWED]->(a1:Arrangement)-[:HAS_TAG]->(t:Tag)
            MATCH (a2:Arrangement)-[:HAS_TAG]->(t)
            WHERE NOT (u)-[:VIEWED]->(a2) AND a1 <> a2
            WITH a2, COUNT(t) AS commonTags
            WHERE commonTags >= 2
            RETURN a2.id AS id,
                   a2.name AS name,
                   a2.description AS description,
                   a2.price AS price,
                   a2.durationDays AS durationDays
            ORDER BY commonTags DESC
            LIMIT 5
        """)
    List<ArrangementRecommendationDto> recommendBasedOnViewed(@Param("userId") Long userId);

    @Query("""
            MATCH (u:User {id: $userId})-[:BOOKED]->(a1:Arrangement)
            MATCH (a1)-[:HAS_TAG]->(t:Tag)
        
            MATCH (other:User)-[:BOOKED]->(a1)
            MATCH (other)-[:BOOKED]->(a2:Arrangement)
            MATCH (a2)-[:HAS_TAG]->(t)
        
            WHERE u <> other
              AND a1 <> a2
              AND NOT (u)-[:BOOKED]->(a2)
        
            WITH a2, COUNT(DISTINCT t) AS commonTags, COUNT(DISTINCT other) AS popularity
            WHERE commonTags >= 1
        
            RETURN a2.id AS id,
                   a2.name AS name,
                   a2.description AS description,
                   a2.price AS price,
                   a2.durationDays AS durationDays
        
            ORDER BY commonTags DESC, popularity DESC
            LIMIT 5
        """)
    List<ArrangementRecommendationDto> recommendBasedOnBooked(@Param("userId") Long userId);
}