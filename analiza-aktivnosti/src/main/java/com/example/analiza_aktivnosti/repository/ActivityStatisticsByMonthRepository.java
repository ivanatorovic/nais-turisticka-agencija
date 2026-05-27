package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.ActivityStatisticsByMonth;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityStatisticsByMonthRepository
        extends CassandraRepository<ActivityStatisticsByMonth, String> {

    @Query("SELECT * FROM activity_statistics_by_month WHERE month = ?0")
    List<ActivityStatisticsByMonth> findByMonth(String month);

    @Query("SELECT * FROM activity_statistics_by_month WHERE month = ?0 AND activity_id = ?1")
    ActivityStatisticsByMonth findOne(String month, Long activityId);

    @Query("DELETE FROM activity_statistics_by_month WHERE month = ?0 AND activity_id = ?1")
    void deleteOne(String month, Long activityId);
}