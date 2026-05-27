package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.ActivityOccupancyByExecution;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityOccupancyByExecutionRepository
        extends CassandraRepository<ActivityOccupancyByExecution, Long> {

    @Query("SELECT * FROM activity_occupancy_by_execution WHERE activity_id = ?0")
    List<ActivityOccupancyByExecution> findByActivityId(Long activityId);

    @Query("SELECT * FROM activity_occupancy_by_execution WHERE activity_id = ?0 AND execution_id = ?1")
    ActivityOccupancyByExecution findOne(Long activityId, Long executionId);

    @Query("DELETE FROM activity_occupancy_by_execution WHERE activity_id = ?0 AND execution_id = ?1")
    void deleteOne(Long activityId, Long executionId);

    @Query("SELECT AVG(occupancy_percent) FROM activity_occupancy_by_execution WHERE activity_id = ?0")
    Double averageOccupancyByActivity(Long activityId);
}