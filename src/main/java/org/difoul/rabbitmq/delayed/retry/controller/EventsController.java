package org.difoul.rabbitmq.delayed.retry.controller;

import org.difoul.rabbitmq.delayed.retry.model.Event;
import org.json.JSONObject;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventsController {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("eventsExchange")
    Exchange eventsExchange;


    @RequestMapping(path = "/send-event", method = RequestMethod.POST)
    public String snedEvent(@RequestBody String body){
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("retries", 3);
        Message message = new Message(body.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend(eventsExchange.getName(), "", message);
        return "message sent";
    }

}
