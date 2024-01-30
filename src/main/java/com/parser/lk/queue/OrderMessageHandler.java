package com.parser.lk.queue;


import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableRabbit
@Component
public class OrderMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageHandler.class);


    @RabbitListener(queues = "orderQueue")
    public void receiverMessage(OrderMessage message) {
        try {
            logger.info("Received message from RabbitMQ: {}", message.toString());
            // Ваш код обработки сообщения
        } catch (Exception e) {
            // Логирование ошибок
            logger.error("Failed to process message", e);
            // Можно бросить AmqpRejectAndDontRequeueException, чтобы сообщить RabbitMQ, что сообщение не должно повторно отправляться
            throw new org.springframework.amqp.AmqpRejectAndDontRequeueException("Failed to process message", e);
        }
    }
}
