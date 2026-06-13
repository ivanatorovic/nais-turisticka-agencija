package com.example.analiza_prodaje.repository;

import com.example.analiza_prodaje.model.CustomerReservationsByMonth;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomerReservationsByMonthRepository
        extends CassandraRepository<CustomerReservationsByMonth, Long> {

    @Query("""
            SELECT * FROM customer_reservations_by_month
            WHERE customer_id = ?0
            """)
    List<CustomerReservationsByMonth> findByCustomerId(Long customerId);

    @Query("""
            SELECT * FROM customer_reservations_by_month
            WHERE customer_id = ?0
            AND month = ?1
            """)
    List<CustomerReservationsByMonth> findByCustomerIdAndMonth(Long customerId, String month);

    @Query("""
            SELECT * FROM customer_reservations_by_month
            WHERE customer_id = ?0
            AND month = ?1
            AND reservation_id = ?2
            """)
    CustomerReservationsByMonth findOne(Long customerId, String month, Long reservationId);

    @Query("""
            DELETE FROM customer_reservations_by_month
            WHERE customer_id = ?0
            AND month = ?1
            AND reservation_id = ?2
            """)
    void deleteOne(Long customerId, String month, Long reservationId);

    @Query("""
            SELECT SUM(total_price) FROM customer_reservations_by_month
            WHERE customer_id = ?0
            AND month = ?1
            """)
    BigDecimal totalRevenueForCustomerInMonth(Long customerId, String month);


    @Query("""
    SELECT customer_id,
           month,
           SUM(total_price) AS total_price
    FROM customer_reservations_by_month
    WHERE customer_id = ?0
    GROUP BY customer_id, month
""")
    List<CustomerReservationsByMonth> revenueByCustomerGroupedByMonth(Long customerId);
}