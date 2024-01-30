package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.services.applicationservice.StatusInterface;

public class ParseStatusService implements StatusInterface {

    private final String prevStatus = "CREATE_ORDER";
    private final String currentStatus = "PARSING";
    private final String nextStatus = "POST_PROCESSING";


    @Override
    public void doProcess() {

    }

    @Override
    public String getStatusName() {
        return "PARSING";
    }
}
