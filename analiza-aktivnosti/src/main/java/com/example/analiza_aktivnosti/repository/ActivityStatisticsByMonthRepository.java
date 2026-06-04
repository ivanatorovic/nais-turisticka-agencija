package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.ActivityStatisticsByMonth;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ActivityStatisticsByMonthRepository
        extends CassandraRepository<ActivityStatisticsByMonth, String> {

    @Query("""
        SELECT * FROM activity_statistics_by_month
        WHERE month = ?0
        LIMIT 3
        """)
    List<ActivityStatisticsByMonth> findTop3ByMonth(String month);

    @Query("""
        SELECT * FROM activity_statistics_by_month
        WHERE month = ?0
        """)
    List<ActivityStatisticsByMonth> findByMonth(String month);

    @Query("""
        SELECT * FROM activity_statistics_by_month
        WHERE month = ?0 AND total_revenue = ?1 AND activity_id = ?2
        """)
    ActivityStatisticsByMonth findOne(String month, BigDecimal totalRevenue, Long activityId);

    @Query("""
        DELETE FROM activity_statistics_by_month
        WHERE month = ?0 AND total_revenue = ?1 AND activity_id = ?2
        """)
    void deleteOne(String month, BigDecimal totalRevenue, Long activityId);
}