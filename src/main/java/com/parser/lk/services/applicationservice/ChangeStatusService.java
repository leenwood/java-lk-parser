package com.parser.lk.services.applicationservice;

import com.parser.lk.entity.Order;
import com.parser.lk.queue.MessageSender;
import com.parser.lk.queue.OrderMessage;
import com.parser.lk.queue.OrderMessageHandler;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.requester.sendrequest.CallbackRequester;
import com.parser.lk.services.requester.sendrequest.dto.NotificationBodyRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

@Service
public class ChangeStatusService {

    private final OrderRepository orderRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderMessageHandler.class);
    private final ApplicationStatusFactory applicationStatusFactory;

    private final CallbackRequester notification;

    private final MessageSender messageSender;

    @Autowired
    public ChangeStatusService(OrderRepository orderRepository, ApplicationStatusFactory applicationStatusFactory, CallbackRequester notification, MessageSender messageSender) {
        this.orderRepository = orderRepository;
        this.applicationStatusFactory = applicationStatusFactory;
        this.notification = notification;
        this.messageSender = messageSender;
    }

    public void changeStatus(OrderMessage message) {

        Order order = this.orderRepository.findById(message.getOrderId()).orElse(null);
        if (order == null) {
            this.logger.warn(String.format("object by id %s not found.", message.getOrderId()));
            return;
        }

        StatusInterface service = this.applicationStatusFactory.getStatusService(
                message.getNextStatus(),
                message.getCurrentStatus()
        );

        if (service.doProcess(message.getOrderId())) {
            this.notification.sendCallback("",
                    new NotificationBodyRequest(
                            message.getCurrentStatus(),
                            message.getNextStatus(),
                            message.getPrevStatus(),
                            true,
                            message.getOrderId())
            );

            order.setStatus(message.getNextStatus());
            this.orderRepository.save(order);

            OrderMessage newMessage = new OrderMessage();
            newMessage.setPrevStatus(message.getCurrentStatus());
            newMessage.setCurrentStatus(message.getNextStatus());
            newMessage.setNextStatus(service.getNextStatusName());
            newMessage.setOrderId(message.getOrderId());

            this.messageSender.sendMessage(newMessage);
        } else {
            this.notification.sendCallback("https://stackoverflow.com",
                    new NotificationBodyRequest(
                            message.getCurrentStatus(),
                            message.getNextStatus(),
                            message.getPrevStatus(),
                            false,
                            message.getOrderId())
            );
        }
    }

}
