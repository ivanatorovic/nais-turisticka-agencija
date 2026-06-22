package rs.ac.uns.acs.nais.recommendation_service.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rs.ac.uns.acs.nais.recommendation_service.config.RabbitMQConfig;
import rs.ac.uns.acs.nais.recommendation_service.messaging.event.SalesUpdatedEvent;

@Component
public class SalesUpdatedListener {

    @RabbitListener(queues = RabbitMQConfig.SALES_UPDATED_QUEUE)
    public void handleSalesUpdated(SalesUpdatedEvent event) {
        System.out.println(
                "SALES UPDATED EVENT PRIMLJEN: sagaId=" + event.getSagaId()
                        + ", reservationId=" + event.getReservationId()
                        + ", userId=" + event.getUserId()
                        + ", arrangementId=" + event.getArrangementId()
        );
    }
}