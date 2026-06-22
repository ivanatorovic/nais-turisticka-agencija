package com.example.analiza_prodaje.messaging.listener;

import com.example.analiza_prodaje.config.RabbitMQConfig;
import com.example.analiza_prodaje.messaging.event.BookingCreatedEvent;
import com.example.analiza_prodaje.messaging.event.SalesFailedEvent;
import com.example.analiza_prodaje.messaging.event.SalesUpdatedEvent;
import com.example.analiza_prodaje.service.SalesByArrangementService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class BookingCreatedListener {

    private final SalesByArrangementService salesByArrangementService;
    private final RabbitTemplate rabbitTemplate;

    public BookingCreatedListener(
            SalesByArrangementService salesByArrangementService,
            RabbitTemplate rabbitTemplate
    ) {
        this.salesByArrangementService = salesByArrangementService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.BOOKING_CREATED_QUEUE)
    public void handleBookingCreated(BookingCreatedEvent event) {
        try {
            salesByArrangementService.createFromBookingEvent(event);

            SalesUpdatedEvent successEvent = new SalesUpdatedEvent(
                    event.getSagaId(),
                    event.getReservationId(),
                    event.getUserId(),
                    event.getArrangementId()
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.SALES_UPDATED_KEY,
                    successEvent
            );

        } catch (Exception e) {
            SalesFailedEvent failedEvent = new SalesFailedEvent(
                    event.getSagaId(),
                    event.getReservationId(),
                    event.getUserId(),
                    event.getArrangementId(),
                    e.getMessage()
            );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.SALES_FAILED_KEY,
                    failedEvent
            );
        }
    }
}