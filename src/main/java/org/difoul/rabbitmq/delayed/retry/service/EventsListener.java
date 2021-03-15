package org.difoul.rabbitmq.delayed.retry.service;

import org.json.JSONObject;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.rabbitmq.client.Channel;

@Service
public class EventsListener {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("throttleExchange")
    Exchange throttleExchange;


    @RabbitListener(queues = "#{eventsQueue.name}", concurrency = "${rabbitmq.conf.events.consumers.count}")
    public void consumeEvents(Message message){
        System.out.println(new String(message.getBody()));
        int retries = (int)message.getMessageProperties().getHeaders().getOrDefault("retries", 0);
        System.out.println(retries + " retries remind");

        if(retries > 0){
            message.getMessageProperties().getHeaders().put("retries", retries-1);
            rabbitTemplate.convertAndSend(throttleExchange.getName(), "", message);
        }else{
            System.out.println("Max retries exceeded!");
        }

    }



}
