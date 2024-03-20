package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import org.springframework.stereotype.Service;


@Service("COMPLETE_WITH_DOCUMENT")
public class CompleteWithDocumentService implements StatusInterface {
    @Override
    public boolean doProcess(Long orderId) {
        return true;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.COMPLETE_WITH_DOCUMENT;
    }

    @Override
    public String getNextStatusName() {
        return null;
    }
}
