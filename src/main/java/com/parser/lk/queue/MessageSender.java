package com.parser.lk.queue;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageSender {

    private final RabbitTemplate rabbitTemplate;



    @Autowired
    public MessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(OrderMessage message)  {

        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        try {
            rabbitTemplate.convertAndSend("orderQueue", message);
        } catch (Throwable e) {
            System.out.println(e.getMessage());
        }

    }

}
