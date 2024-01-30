package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.services.applicationservice.StatusInterface;
import org.springframework.stereotype.Service;

@Service
public class ParseStatusService implements StatusInterface {


    private final String statusName = "PARSING";


    @Override
    public void doProcess() {

    }

    @Override
    public String getStatusName() {
        return this.statusName;
    }
}
