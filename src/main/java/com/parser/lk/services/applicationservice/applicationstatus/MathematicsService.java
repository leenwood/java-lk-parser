package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("MathematicsService")
public class MathematicsService implements StatusInterface {

    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(MathematicsService.class);

    public MathematicsService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public boolean doProcess(Long orderId) {

        Optional<Order> optionalOrder = this.orderRepository.findOneById(orderId);

        if (optionalOrder.isEmpty()) {
            this.logger.error(String.format("order by id %s not found", orderId));
            return false;
        }

        Order order = optionalOrder.get();

        

        return false;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.MATHEMATICS;
    }

    @Override
    public String getNextStatusName() {
        return NameStatusServiceEnum.COMPLETE;
    }
}
