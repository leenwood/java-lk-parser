package com.parser.lk.services.applicationservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusFactory {

    private final Logger logger = LoggerFactory.getLogger(ApplicationStatusFactory.class);

    public StatusInterface getStatusService(String nextStatusOrder, String currentStatusOrder) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        switch (nextStatusOrder) {
            case "PARSING":
                return (StatusInterface) context.getBean("ParseStatusService");
            default:
                logger.error(String.format("Invalid status order: %s. (Not found)", nextStatusOrder));
                return null;
        }
    }

}
