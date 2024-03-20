package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.documentmanager.FilesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("CREATING_DOCUMENT")
public class CreatingDocumentService implements StatusInterface {

    private final FilesManager filesManager;

    private final Logger logger = LoggerFactory.getLogger(CreatingDocumentService.class);

    public CreatingDocumentService(FilesManager filesManager) {
        this.filesManager = filesManager;
    }

    @Override
    public boolean doProcess(Long orderId) {
        boolean result = false;
        try {
            result = this.filesManager.createExcelDocumentByOrderId(orderId);
        } catch (Throwable throwable) {
            this.logger.error(throwable.toString());
        }

        return result;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.CREATING_DOCUMENT;
    }

    @Override
    public String getNextStatusName() {
        return NameStatusServiceEnum.COMPLETE_WITH_DOCUMENT;
    }
}
