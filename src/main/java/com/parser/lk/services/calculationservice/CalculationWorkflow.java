package com.parser.lk.services.calculationservice;


import com.parser.lk.services.calculationservice.mathematics.method.MathematicsMethodInterface;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CalculationWorkflow {

    private final ApplicationContext context;

    public CalculationWorkflow(ApplicationContext context) {
        this.context = context;
    }


    public void baseWorkflow(Long orderId) {
        Map<String, MathematicsMethodInterface> methods = context.getBeansOfType(MathematicsMethodInterface.class);
        for (MathematicsMethodInterface method : methods.values()) {
            method.calculate(orderId);
        }
    }

}
