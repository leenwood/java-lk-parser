package com.parser.lk.services.parsingmanager;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.parser.lk.dto.request.analytics.CreateOrderRequest;
import com.parser.lk.entity.Order;
import com.parser.lk.queue.MessageSender;
import com.parser.lk.queue.OrderMessage;
import com.parser.lk.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParserManager {

    private final MessageSender messageSender;

    private final OrderRepository orderRepository;

    @Autowired
    public ParserManager(MessageSender messageSender, OrderRepository orderRepository) {
        this.messageSender = messageSender;
        this.orderRepository = orderRepository;
    }


    public boolean startWork(CreateOrderRequest createOrderObject) {
        Order order = new Order();
        order.setAllRegion(createOrderObject.isAllRegion());
        order.setHasSalary(createOrderObject.getHasSalary());
        order.setHasVmi(createOrderObject.getHasVmi());
        order.setStatus("START_PARSING");
        order.setTimestamp(createOrderObject.getTimestamp());
        order.setExternalId(createOrderObject.getExternalId());
        order.setRegionId(createOrderObject.getRegionId());
        order.setSearchText(createOrderObject.getSearchText());

        var saveOrder = this.orderRepository.save(order);

        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setOrderId(saveOrder.getId());
        orderMessage.setNextStatus("PARSING");
        orderMessage.setCurrentStatus("START_PARSING");
        orderMessage.setPrevStatus(null);

        this.messageSender.sendMessage(orderMessage);
        return true;
    }


}
