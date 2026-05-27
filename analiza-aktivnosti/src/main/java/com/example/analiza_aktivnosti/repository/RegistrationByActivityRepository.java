package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.RegistrationByActivity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationByActivityRepository extends CassandraRepository<RegistrationByActivity, Long> {

    @Query("SELECT * FROM registrations_by_activity WHERE activity_id = ?0")
    List<RegistrationByActivity> findByActivityId(Long activityId);

    @Query("SELECT * FROM registrations_by_activity WHERE activity_id = ?0 AND registration_id = ?1")
    RegistrationByActivity findOne(Long activityId, Long registrationId);

    @Query("DELETE FROM registrations_by_activity WHERE activity_id = ?0 AND registration_id = ?1")
    void deleteOne(Long activityId, Long registrationId);

    @Query("SELECT SUM(number_of_people) FROM registrations_by_activity WHERE activity_id = ?0")
    Long countPeopleByActivity(Long activityId);
}