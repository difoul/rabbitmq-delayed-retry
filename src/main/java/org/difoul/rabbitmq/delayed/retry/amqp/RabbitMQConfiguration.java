package org.difoul.rabbitmq.delayed.retry.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.conf.throttle.exchange.name}")
    String throttleExchangeName;

    @Value("${rabbitmq.conf.throttle.queue.name}")
    String throttleQueueName;

    @Value("${rabbitmq.conf.events.exchange.name}")
    String eventsExchangeName;

    @Value("${rabbitmq.conf.events.queue.name}")
    String eventsQueueName;

    @Value("${rabbitmq.conf.message.ttl}")
    int messageTTL;

    @Bean(name = "throttleExchange")
    TopicExchange throttleExchange(){
        return  new TopicExchange(throttleExchangeName);
    }

    @Bean(name = "throttleQueue")
    Queue throttleQueue(){
        return QueueBuilder.durable(throttleQueueName)
                .withArgument("x-dead-letter-exchange", eventsExchangeName)
                .withArgument("x-message-ttl", messageTTL)
                .build();
    }

    @Bean(name = "throttleQueueBinding")
    Binding throttleQueueBinding(){
        return BindingBuilder.bind(throttleQueue())
                .to(throttleExchange())
                .with("#");
    }


    @Bean(name = "eventsExchange")
    TopicExchange eventsExchange(){
        return  new TopicExchange(eventsExchangeName);
    }

    @Bean(name = "eventsQueue")
    Queue eventsQueue(){
        return QueueBuilder.durable(eventsQueueName)
                .build();
    }

    @Bean(name = "eventsQueueBinding")
    Binding eventsQueueBinding(){
        return BindingBuilder.bind(eventsQueue())
                .to(eventsExchange())
                .with("#");
    }




}
