package com.turisticka_agencija.dodatne_aktivnosti.messaging.listener;

import com.turisticka_agencija.dodatne_aktivnosti.config.RabbitMQConfig;
import com.turisticka_agencija.dodatne_aktivnosti.messaging.event.ComplaintCounterFailedEvent;
import com.turisticka_agencija.dodatne_aktivnosti.messaging.event.ComplaintCounterUpdatedEvent;
import com.turisticka_agencija.dodatne_aktivnosti.messaging.event.ComplaintCreatedEvent;
import com.turisticka_agencija.dodatne_aktivnosti.service.IAdditionalActivityService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ComplaintCreatedListener {

    private final IAdditionalActivityService additionalActivityService;
    private final RabbitTemplate rabbitTemplate;

    public ComplaintCreatedListener(
            IAdditionalActivityService additionalActivityService,
            RabbitTemplate rabbitTemplate
    ) {
        this.additionalActivityService = additionalActivityService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.COMPLAINT_CREATED_QUEUE)
    public void handleComplaintCreated(ComplaintCreatedEvent event) {

        try {
            additionalActivityService.handleComplaintCreated(
                    event.getComplaintId(),
                    event.getActivityId(),
                    event.getCustomerId()
            );

            ComplaintCounterUpdatedEvent successEvent =
                    new ComplaintCounterUpdatedEvent(
                            event.getSagaId(),
                            event.getComplaintId(),
                            event.getActivityId(),
                            event.getCustomerId()
                    );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CHOREOGRAPHY_EXCHANGE,
                    RabbitMQConfig.COMPLAINT_COUNTER_UPDATED_KEY,
                    successEvent
            );

        } catch (Exception e) {

            ComplaintCounterFailedEvent failedEvent =
                    new ComplaintCounterFailedEvent(
                            event.getSagaId(),
                            event.getComplaintId(),
                            event.getActivityId(),
                            event.getCustomerId(),
                            e.getMessage()
                    );

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.CHOREOGRAPHY_EXCHANGE,
                    RabbitMQConfig.COMPLAINT_COUNTER_FAILED_KEY,
                    failedEvent
            );
        }
    }
}