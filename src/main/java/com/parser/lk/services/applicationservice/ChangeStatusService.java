package com.parser.lk.services.applicationservice;

import com.parser.lk.entity.Order;
import com.parser.lk.queue.MessageSender;
import com.parser.lk.queue.OrderMessage;
import com.parser.lk.queue.OrderMessageHandler;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.parsingmanager.ParserManager;
import com.parser.lk.services.requester.sendrequest.CallbackRequester;
import com.parser.lk.services.requester.sendrequest.dto.NotificationBodyRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;

import java.util.Objects;

@Service
public class ChangeStatusService {

    private final OrderRepository orderRepository;
    private final Logger logger = LoggerFactory.getLogger(OrderMessageHandler.class);
    private final ApplicationStatusFactory applicationStatusFactory;

    private final ParserManager parserManager;

    private final CallbackRequester notification;


    @Value("${application.callback.lk.users}")
    private String callbackUrl;

    @Autowired
    public ChangeStatusService(
            OrderRepository orderRepository,
            ApplicationStatusFactory applicationStatusFactory,
            ParserManager parserManager,
            CallbackRequester notification
    ) {
        this.orderRepository = orderRepository;
        this.applicationStatusFactory = applicationStatusFactory;
        this.parserManager = parserManager;
        this.notification = notification;
    }

    public void changeStatus(OrderMessage message) {

        Order order = this.orderRepository.findById(message.getOrderId()).orElse(null);
        if (order == null) {
            this.logger.warn(String.format("object by id %s not found.", message.getOrderId()));
            return;
        }

        System.out.println(message);

        StatusInterface service = this.applicationStatusFactory.getStatusService(
                message.getNextStatus(),
                message.getCurrentStatus()
        );

        if (service.doProcess(message.getOrderId())) {
            this.notification.sendCallback(this.callbackUrl,
                    new NotificationBodyRequest(
                            message.getCurrentStatus(),
                            message.getNextStatus(),
                            message.getPrevStatus(),
                            true,
                            message.getOrderId())
            );


            order.setStatus(service.getStatusName());
            this.orderRepository.save(order);

            if (service.getNextStatusName() != null) {
                this.parserManager.changeStatusQueue(
                        message.getCurrentStatus(),
                        service.getNextStatusName(),
                        service.getStatusName(),
                        message.getOrderId()
                );
            } else if (Objects.equals(service.getStatusName(), NameStatusServiceEnum.COMPLETE)) {
                return;
            }

        } else {
            this.notification.sendCallback(this.callbackUrl,
                    new NotificationBodyRequest(
                            message.getCurrentStatus(),
                            message.getNextStatus(),
                            message.getPrevStatus(),
                            false,
                            message.getOrderId())
            );

            if (service.getNextStatusName() != null) {
                this.parserManager.changeStatusQueue(
                        message.getCurrentStatus(),
                        NameStatusServiceEnum.PARSING_ERROR,
                        service.getStatusName(),
                        message.getOrderId()
                );
            }

        }
    }

}
