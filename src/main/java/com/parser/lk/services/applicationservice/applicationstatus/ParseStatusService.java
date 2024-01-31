package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import org.springframework.stereotype.Service;

@Service("ParseStatusService")
public class ParseStatusService implements StatusInterface {

    private final HeadHunterRequester headHunterRequester;

    private final OrderRepository orderRepository;


    private final String statusName = "PARSING";

    private final String nextStatus = "POST_PROCESSING";


    public ParseStatusService(
            HeadHunterRequester headHunterRequester,
            OrderRepository orderRepository
    ) {
        this.headHunterRequester = headHunterRequester;
        this.orderRepository = orderRepository;
    }


    @Override
    public boolean doProcess(Long orderId) {

        return false;
    }

    @Override
    public String getStatusName() {
        return this.statusName;
    }

    @Override
    public String getNextStatusName() {
        return this.nextStatus;
    }
}
