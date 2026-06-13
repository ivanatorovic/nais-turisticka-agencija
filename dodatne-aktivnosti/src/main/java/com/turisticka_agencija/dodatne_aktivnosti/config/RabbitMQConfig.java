package com.turisticka_agencija.dodatne_aktivnosti.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String CHOREOGRAPHY_EXCHANGE =
            "saga.choreography.exchange";

    public static final String COMPLAINT_CREATED_QUEUE =
            "complaint.created.queue";

    public static final String COMPLAINT_CREATED_KEY =
            "complaint.created";

    public static final String COMPLAINT_COUNTER_UPDATED_QUEUE =
            "complaint.counter.updated.queue";

    public static final String COMPLAINT_COUNTER_UPDATED_KEY =
            "complaint.counter.updated";

    public static final String COMPLAINT_COUNTER_FAILED_QUEUE =
            "complaint.counter.failed.queue";

    public static final String COMPLAINT_COUNTER_FAILED_KEY =
            "complaint.counter.failed";

    @Bean
    public TopicExchange choreographyExchange() {
        return new TopicExchange(CHOREOGRAPHY_EXCHANGE, true, false);
    }

    @Bean
    public Queue complaintCreatedQueue() {
        return QueueBuilder.durable(COMPLAINT_CREATED_QUEUE).build();
    }

    @Bean
    public Queue complaintCounterUpdatedQueue() {
        return QueueBuilder.durable(COMPLAINT_COUNTER_UPDATED_QUEUE).build();
    }

    @Bean
    public Queue complaintCounterFailedQueue() {
        return QueueBuilder.durable(COMPLAINT_COUNTER_FAILED_QUEUE).build();
    }

    @Bean
    public Binding complaintCreatedBinding() {
        return BindingBuilder
                .bind(complaintCreatedQueue())
                .to(choreographyExchange())
                .with(COMPLAINT_CREATED_KEY);
    }

    @Bean
    public Binding complaintCounterUpdatedBinding() {
        return BindingBuilder
                .bind(complaintCounterUpdatedQueue())
                .to(choreographyExchange())
                .with(COMPLAINT_COUNTER_UPDATED_KEY);
    }

    @Bean
    public Binding complaintCounterFailedBinding() {
        return BindingBuilder
                .bind(complaintCounterFailedQueue())
                .to(choreographyExchange())
                .with(COMPLAINT_COUNTER_FAILED_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}