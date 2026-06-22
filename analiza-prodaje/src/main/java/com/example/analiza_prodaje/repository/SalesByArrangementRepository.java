package com.example.analiza_prodaje.repository;

import com.example.analiza_prodaje.model.SalesByArrangement;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface SalesByArrangementRepository
        extends CassandraRepository<SalesByArrangement, Long> {

    @Query("SELECT * FROM sales_by_arrangement WHERE arrangement_id = ?0")
    List<SalesByArrangement> findByArrangementId(Long arrangementId);

    @Query("""
            SELECT * FROM sales_by_arrangement
            WHERE arrangement_id = ?0 AND reservation_id = ?1
            """)
    SalesByArrangement findOne(Long arrangementId, Long reservationId);

    @Query("""
            DELETE FROM sales_by_arrangement
            WHERE arrangement_id = ?0 AND reservation_id = ?1
            """)
    void deleteOne(Long arrangementId, Long reservationId);

    @Query("""
            SELECT SUM(total_price) FROM sales_by_arrangement
            WHERE arrangement_id = ?0
            """)
    BigDecimal sumRevenueByArrangement(Long arrangementId);

    @Query("""
        SELECT * FROM sales_by_arrangement
        WHERE reservation_id = ?0
        ALLOW FILTERING
        """)
    List<SalesByArrangement> findByReservationId(Long reservationId);
}