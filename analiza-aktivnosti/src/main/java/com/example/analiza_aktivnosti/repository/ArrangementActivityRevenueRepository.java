package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.ArrangementActivityRevenue;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArrangementActivityRevenueRepository
        extends CassandraRepository<ArrangementActivityRevenue, Long> {

    @Query("""
            SELECT * FROM arrangement_activity_revenue
            WHERE arrangement_id = ?0
            """)
    List<ArrangementActivityRevenue> findByArrangementId(
            Long arrangementId
    );

    @Query("""
            SELECT * FROM arrangement_activity_revenue
            WHERE arrangement_id = ?0
            AND activity_id = ?1
            AND registration_id = ?2
            """)
    ArrangementActivityRevenue findOne(
            Long arrangementId,
            Long activityId,
            Long registrationId
    );

    @Query("""
            DELETE FROM arrangement_activity_revenue
            WHERE arrangement_id = ?0
            AND activity_id = ?1
            AND registration_id = ?2
            """)
    void deleteOne(
            Long arrangementId,
            Long activityId,
            Long registrationId
    );

    @Query("""
        SELECT arrangement_id,
        activity_id,
        SUM(total_price) AS total_price
        FROM arrangement_activity_revenue
        WHERE arrangement_id = ?0
        GROUP BY arrangement_id, activity_id
        """)
    List<ArrangementActivityRevenue> revenueByArrangement(Long arrangementId);
}