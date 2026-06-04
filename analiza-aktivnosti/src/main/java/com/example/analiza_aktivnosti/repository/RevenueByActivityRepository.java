package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.RevenueByActivity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RevenueByActivityRepository extends CassandraRepository<RevenueByActivity, Long> {

    @Query("SELECT * FROM revenue_by_activity WHERE activity_id = ?0")
    List<RevenueByActivity> findByActivityId(Long activityId);

    @Query("SELECT * FROM revenue_by_activity WHERE activity_id = ?0 AND registration_id = ?1")
    RevenueByActivity findOne(Long activityId, Long registrationId);

    @Query("DELETE FROM revenue_by_activity WHERE activity_id = ?0 AND registration_id = ?1")
    void deleteOne(Long activityId, Long registrationId);

    @Query("SELECT SUM(total_price) FROM revenue_by_activity WHERE activity_id = ?0")
    BigDecimal sumRevenueByActivity(Long activityId);
}