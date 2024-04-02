package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.calculationservice.CalculationWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("MathematicsService")
public class MathematicsService extends BaseStatusService implements StatusInterface {

    private final CalculationWorkflow calculationWorkflow;

    public MathematicsService(
            CalculationWorkflow calculationWorkflow
    ) {
        this.calculationWorkflow = calculationWorkflow;
    }


    @Override
    public boolean doProcess(Long orderId) {
        if (!this.trySetupOrder(orderId)) {
            return false;
        }

        this.calculationWorkflow.baseWorkflow(this.order.getId());

        return true;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.MATHEMATICS;
    }

    @Override
    public String getNextStatusName() {
        return NameStatusServiceEnum.COMPLETE;
    }
}
