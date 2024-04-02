package com.parser.lk.services.applicationservice;

import com.parser.lk.services.applicationservice.applicationstatus.StatusInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ApplicationStatusFactory {

    @Autowired
    private ApplicationContext context;

    private final Logger logger = LoggerFactory.getLogger(ApplicationStatusFactory.class);

    public StatusInterface getStatusService(String serviceAlias) {
        Map<String, StatusInterface> services = context.getBeansOfType(StatusInterface.class);
        for (StatusInterface service : services.values()) {
            if (serviceAlias.equals(service.getStatusName())) {
                return service;
            }
        }
        throw new IllegalArgumentException(String.format("Not found service with alias %s", serviceAlias));
    }

}
