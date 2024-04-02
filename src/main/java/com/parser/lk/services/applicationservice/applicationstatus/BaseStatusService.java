package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

abstract public class BaseStatusService {

    @Autowired
    protected OrderRepository orderRepository;

    protected Logger logger = LoggerFactory.getLogger(this.getClass().getName());;

    protected Order order;

    protected boolean trySetupOrder(Long orderId) {
        orderId = 1L;
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            this.logger.error(String.format("order by id %s not found", orderId));
            return false;
        }
        this.order = orderOptional.get();
        return true;
    }

}
