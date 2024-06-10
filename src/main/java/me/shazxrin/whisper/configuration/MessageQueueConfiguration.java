package me.shazxrin.whisper.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfiguration {
    private final String notificationQueueName;

    public MessageQueueConfiguration(
        @Value("${whisper.notification-queue-name}") String notificationQueueName
    ) {
        this.notificationQueueName = notificationQueueName;
    }

    @Bean
    public Queue queue() {
        return new Queue(this.notificationQueueName, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
