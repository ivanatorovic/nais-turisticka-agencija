package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.RegistrationByStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationByStatusRepository
        extends CassandraRepository<RegistrationByStatus, String> {

    @Query("SELECT * FROM registrations_by_status WHERE status = ?0")
    List<RegistrationByStatus> findByStatus(String status);

    @Query("SELECT * FROM registrations_by_status WHERE status = ?0 AND registration_id = ?1")
    RegistrationByStatus findOne(String status, Long registrationId);

    @Query("DELETE FROM registrations_by_status WHERE status = ?0 AND registration_id = ?1")
    void deleteOne(String status, Long registrationId);
}