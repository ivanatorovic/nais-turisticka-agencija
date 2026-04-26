package com.turisticka_agencija.dodatne_aktivnosti.repository;

import com.turisticka_agencija.dodatne_aktivnosti.model.Customer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends Neo4jRepository<Customer, Long> {

    @Query("""
        MATCH (c:Customer)
        WHERE c.customerId = $customerId
        MATCH (a:AdditionalActivity)
        WHERE a.activityId = $activityId
        MERGE (c)-[r:REGISTERED_FOR]->(a)
        ON CREATE SET r.registrationDate = $registrationDate,
                      r.numberOfPeople = $numberOfPeople
        ON MATCH SET r.registrationDate = $registrationDate,
                     r.numberOfPeople = $numberOfPeople
        RETURN c
    """)
    Customer registerOrUpdateActivity(@Param("customerId") Long customerId,
                                      @Param("activityId") Long activityId,
                                      @Param("registrationDate") java.time.LocalDate registrationDate,
                                      @Param("numberOfPeople") Integer numberOfPeople);

    @Query("""
        MATCH (c:Customer)-[r:REGISTERED_FOR]->(a:AdditionalActivity)
        WHERE c.customerId = $customerId
        AND a.activityId = $activityId
        DELETE r
    """)
    void deleteRegistrationForActivity(@Param("customerId") Long customerId,
                                       @Param("activityId") Long activityId);
}