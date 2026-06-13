package com.example.analiza_prodaje.repository;

import com.example.analiza_prodaje.model.DestinationSalesByMonth;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;

import java.util.List;

public interface DestinationSalesByMonthRepository
        extends CassandraRepository<DestinationSalesByMonth, String> {

    @Query("""
            SELECT * FROM destination_sales_by_month
            WHERE month = ?0
            """)
    List<DestinationSalesByMonth> findByMonth(String month);

    @Query("""
            SELECT month,
                   destination,
                   SUM(total_price) AS total_price
            FROM destination_sales_by_month
            WHERE month = ?0
            GROUP BY month, destination
            """)
    List<DestinationSalesByMonth> revenueByDestination(String month);


    @Query("""
        SELECT * FROM destination_sales_by_month
        WHERE month = ?0
        AND destination = ?1
        AND reservation_id = ?2
        """)
    DestinationSalesByMonth findOne(
            String month,
            String destination,
            Long reservationId
    );

    @Query("""
        DELETE FROM destination_sales_by_month
        WHERE month = ?0
        AND destination = ?1
        AND reservation_id = ?2
        """)
    void deleteOne(
            String month,
            String destination,
            Long reservationId
    );
}