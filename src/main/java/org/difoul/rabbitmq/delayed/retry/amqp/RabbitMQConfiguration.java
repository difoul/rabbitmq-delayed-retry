package org.difoul.rabbitmq.delayed.retry.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.conf.retry.exchange.name}")
    String retryExchangeName;

    @Value("${rabbitmq.conf.retry.queue.name}")
    String retryQueueName;

    @Value("${rabbitmq.conf.events.exchange.name}")
    String eventsExchangeName;

    @Value("${rabbitmq.conf.events.queue.name}")
    String eventsQueueName;

    @Value("${rabbitmq.conf.message.ttl}")
    int messageTTL;

    @Bean(name = "retryExchange")
    TopicExchange retryExchange(){
        return  new TopicExchange(retryExchangeName);
    }

    @Bean(name = "retryQueue")
    Queue retryQueue(){
        return QueueBuilder.durable(retryQueueName)
                .withArgument("x-dead-letter-exchange", eventsExchangeName)
                .withArgument("x-message-ttl", messageTTL)
                .build();
    }

    @Bean(name = "retryQueueBinding")
    Binding retryQueueBinding(){
        return BindingBuilder.bind(retryQueue())
                .to(retryExchange())
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
