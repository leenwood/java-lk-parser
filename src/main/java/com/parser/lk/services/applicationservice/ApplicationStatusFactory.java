package com.parser.lk.services.applicationservice;

import com.parser.lk.services.applicationservice.applicationstatus.ParseStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusFactory {

    private final ParseStatusService parseStatusService;

    private final Logger logger = LoggerFactory.getLogger(ApplicationStatusFactory.class);

    @Autowired
    public ApplicationStatusFactory(ParseStatusService parseStatusService) {
        this.parseStatusService = parseStatusService;
    }

    public StatusInterface getStatusService(String nextStatusOrder, String currentStatusOrder) {
        switch (nextStatusOrder) {
            case "PARSING":
                return this.parseStatusService;
            default:
                logger.error(String.format("Invalid status order: %s. (Not found)", nextStatusOrder));
                return null;
        }
    }

}
