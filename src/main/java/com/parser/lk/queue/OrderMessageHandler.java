package com.parser.lk.queue;


import com.parser.lk.services.applicationservice.ChangeStatusService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@EnableRabbit
@Component
public class OrderMessageHandler {

    private final ChangeStatusService changeStatusService;


    @Autowired
    public OrderMessageHandler(ChangeStatusService changeStatusService) {
        this.changeStatusService = changeStatusService;
    }

    @RabbitListener(queues = "orderQueue")
    public void receiverMessage(OrderMessage message) {
        try {
            this.changeStatusService.changeStatus(message);
        } catch (Throwable throwable) {
            System.out.println(throwable.toString());
        }
    }
}
