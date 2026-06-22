package rs.ac.uns.acs.nais.recommendation_service.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import rs.ac.uns.acs.nais.recommendation_service.config.RabbitMQConfig;
import rs.ac.uns.acs.nais.recommendation_service.messaging.event.SalesFailedEvent;
import rs.ac.uns.acs.nais.recommendation_service.service.impl.UserService;

@Component
public class SalesFailedListener {

    private final UserService userService;

    public SalesFailedListener(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = RabbitMQConfig.SALES_FAILED_QUEUE)
    public void handleSalesFailed(SalesFailedEvent event) {

        Integer before = userService.getBookedCount(
                event.getUserId(),
                event.getArrangementId()
        );

        System.out.println("SALES FAILED EVENT PRIMLJEN: " + event.getReason());
        System.out.println("COUNT PRE KOMPENZACIJE = " + before);

        userService.compensateBookedRelationship(
                event.getUserId(),
                event.getArrangementId()
        );

        Integer after = userService.getBookedCount(
                event.getUserId(),
                event.getArrangementId()
        );

        System.out.println("COUNT POSLE KOMPENZACIJE = " + after);
    }
}