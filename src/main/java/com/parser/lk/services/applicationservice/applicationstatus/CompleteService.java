package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;

public class CompleteService implements StatusInterface {
    @Override
    public boolean doProcess(Long orderId) {
        return false;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.COMPLETE;
    }

    @Override
    public String getNextStatusName() {
        return NameStatusServiceEnum.COMPLETE;
    }
}
