package com.example.analiza_prodaje.repository;

import com.example.analiza_prodaje.model.ReservationsByStatus;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationsByStatusRepository
        extends CassandraRepository<ReservationsByStatus, String> {

    @Query("SELECT * FROM reservations_by_status WHERE status = ?0")
    List<ReservationsByStatus> findByStatus(String status);

    @Query("""
            SELECT * FROM reservations_by_status
            WHERE status = ?0
            AND reservation_date >= ?1
            AND reservation_date <= ?2
            """)
    List<ReservationsByStatus> findByStatusAndDateRange(
            String status,
            LocalDate from,
            LocalDate to
    );

    @Query("""
            SELECT * FROM reservations_by_status
            WHERE status = ?0
            AND reservation_date = ?1
            AND reservation_id = ?2
            """)
    ReservationsByStatus findOne(
            String status,
            LocalDate reservationDate,
            Long reservationId
    );

    @Query("""
            DELETE FROM reservations_by_status
            WHERE status = ?0
            AND reservation_date = ?1
            AND reservation_id = ?2
            """)
    void deleteOne(
            String status,
            LocalDate reservationDate,
            Long reservationId
    );
}