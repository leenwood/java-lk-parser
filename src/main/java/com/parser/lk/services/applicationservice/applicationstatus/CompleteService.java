package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import org.springframework.stereotype.Service;

@Service
public class CompleteService implements StatusInterface {
    @Override
    public boolean doProcess(Long orderId) {
        return true;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.COMPLETE;
    }

    @Override
    public String getNextStatusName() {
        return null;
    }
}
