package com.parser.lk.services.applicationservice;

import com.parser.lk.entity.Order;
import com.parser.lk.queue.OrderMessage;
import com.parser.lk.queue.OrderMessageHandler;
import com.parser.lk.repository.OrderRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

@Service
public class ChangeStatusService {

    private final OrderRepository orderRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderMessageHandler.class);
    private final ApplicationStatusFactory applicationStatusFactory;

    @Autowired
    public ChangeStatusService(OrderRepository orderRepository, ApplicationStatusFactory applicationStatusFactory) {
        this.orderRepository = orderRepository;
        this.applicationStatusFactory = applicationStatusFactory;
    }

    public void changeStatus(OrderMessage message) {

        Order order = this.orderRepository.findById(message.getOrderId()).orElse(null);
        if (order == null) {
            this.logger.warn(String.format("object by id %s not found.", message.getOrderId()));
            return ;
        }

        StatusInterface service = this.applicationStatusFactory.getStatusService(
                message.getNextStatus(),
                message.getCurrentStatus()
        );

        service.doProcess();


    }

}
