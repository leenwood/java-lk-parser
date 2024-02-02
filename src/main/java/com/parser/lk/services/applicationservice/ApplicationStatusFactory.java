package com.parser.lk.services.applicationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusFactory {

    @Autowired
    private ApplicationContext context;

    private final Logger logger = LoggerFactory.getLogger(ApplicationStatusFactory.class);

    public StatusInterface getStatusService(String nextStatusOrder, String currentStatusOrder) {

        switch (nextStatusOrder) {
            case "PARSING":
                return this.context.getBean("ParseStatusService", StatusInterface.class);
            default:
                logger.error(String.format("Invalid status order: %s. (Not found)", nextStatusOrder));
                return null;
        }
    }

}
