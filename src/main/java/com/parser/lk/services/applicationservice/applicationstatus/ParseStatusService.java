package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ParseStatusService")
public class ParseStatusService implements StatusInterface {

    private final VacanciesParser vacanciesParser;

    private final OrderRepository orderRepository;


    private final String statusName = "PARSING";

    private final String nextStatus = "POST_PROCESSING";


    @Autowired
    public ParseStatusService(
            VacanciesParser vacanciesParser,
            OrderRepository orderRepository
    ) {
        this.vacanciesParser = vacanciesParser;
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
