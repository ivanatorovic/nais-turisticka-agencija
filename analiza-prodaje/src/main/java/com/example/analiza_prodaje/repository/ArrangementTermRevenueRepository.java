package com.example.analiza_prodaje.repository;

import com.example.analiza_prodaje.model.ArrangementTermRevenue;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrangementTermRevenueRepository
        extends CassandraRepository<ArrangementTermRevenue, Long> {

    @Query("""
            SELECT * FROM arrangement_term_revenue
            WHERE arrangement_id = ?0
            """)
    List<ArrangementTermRevenue> findByArrangementId(Long arrangementId);

    @Query("""
            SELECT * FROM arrangement_term_revenue
            WHERE arrangement_id = ?0
            AND term_id = ?1
            AND reservation_id = ?2
            """)
    ArrangementTermRevenue findOne(Long arrangementId, Long termId, Long reservationId);

    @Query("""
            DELETE FROM arrangement_term_revenue
            WHERE arrangement_id = ?0
            AND term_id = ?1
            AND reservation_id = ?2
            """)
    void deleteOne(Long arrangementId, Long termId, Long reservationId);

    @Query("""
            SELECT arrangement_id,
                   term_id,
                   SUM(total_price) AS total_price
            FROM arrangement_term_revenue
            WHERE arrangement_id = ?0
            GROUP BY arrangement_id, term_id
            """)
    List<ArrangementTermRevenue> revenueByArrangement(Long arrangementId);

    @Query("""
    SELECT arrangement_id,
           term_id,
           SUM(number_of_people) AS number_of_people
    FROM arrangement_term_revenue
    WHERE arrangement_id = ?0
    GROUP BY arrangement_id, term_id
    """)
    List<ArrangementTermRevenue> totalPeopleByTerm(Long arrangementId);




}