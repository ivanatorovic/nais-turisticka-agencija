package com.example.analiza_prodaje.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "booking.saga.exchange";

    public static final String BOOKING_CREATED_QUEUE = "booking.created.queue";
    public static final String SALES_UPDATED_QUEUE = "sales.updated.queue";
    public static final String SALES_FAILED_QUEUE = "sales.failed.queue";

    public static final String BOOKING_CREATED_KEY = "booking.created";
    public static final String SALES_UPDATED_KEY = "sales.updated";
    public static final String SALES_FAILED_KEY = "sales.failed";

    @Bean
    public TopicExchange bookingSagaExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue bookingCreatedQueue() {
        return QueueBuilder.durable(BOOKING_CREATED_QUEUE).build();
    }

    @Bean
    public Queue salesUpdatedQueue() {
        return QueueBuilder.durable(SALES_UPDATED_QUEUE).build();
    }

    @Bean
    public Queue salesFailedQueue() {
        return QueueBuilder.durable(SALES_FAILED_QUEUE).build();
    }

    @Bean
    public Binding bookingCreatedBinding() {
        return BindingBuilder.bind(bookingCreatedQueue())
                .to(bookingSagaExchange())
                .with(BOOKING_CREATED_KEY);
    }

    @Bean
    public Binding salesUpdatedBinding() {
        return BindingBuilder.bind(salesUpdatedQueue())
                .to(bookingSagaExchange())
                .with(SALES_UPDATED_KEY);
    }

    @Bean
    public Binding salesFailedBinding() {
        return BindingBuilder.bind(salesFailedQueue())
                .to(bookingSagaExchange())
                .with(SALES_FAILED_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConverter
    ) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        return template;
    }
}