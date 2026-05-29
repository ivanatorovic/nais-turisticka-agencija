package com.example.analiza_aktivnosti.repository;

import com.example.analiza_aktivnosti.entity.RegistrationByStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistrationByStatusRepository
        extends CassandraRepository<RegistrationByStatus, String> {

    @Query("SELECT * FROM registrations_by_status WHERE status = ?0")
    List<RegistrationByStatus> findByStatus(String status);

    @Query("""
            SELECT * FROM registrations_by_status
            WHERE status = ?0
            AND registration_date >= ?1
            AND registration_date <= ?2
            """)
    List<RegistrationByStatus> findByStatusAndDateRange(
            String status,
            LocalDate from,
            LocalDate to
    );

    @Query("""
            SELECT * FROM registrations_by_status
            WHERE status = ?0
            AND registration_date = ?1
            AND registration_id = ?2
            """)
    RegistrationByStatus findOne(
            String status,
            LocalDate registrationDate,
            Long registrationId
    );

    @Query("""
            DELETE FROM registrations_by_status
            WHERE status = ?0
            AND registration_date = ?1
            AND registration_id = ?2
            """)
    void deleteOne(
            String status,
            LocalDate registrationDate,
            Long registrationId
    );
}