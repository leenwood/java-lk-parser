package com.parser.lk.services.calculationservice;


import com.parser.lk.dto.response.analytics.MathematicsResult;
import com.parser.lk.entity.CalculationResults;
import com.parser.lk.repository.CalculationResultsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MathematicsManager {

    private final CalculationResultsRepository calculationResultsRepository;


    public MathematicsManager(CalculationResultsRepository calculationResultsRepository) {
        this.calculationResultsRepository = calculationResultsRepository;
    }


    public List<MathematicsResult> getResultByGuid(String guid) {
        List<MathematicsResult> mathematicsResults = new ArrayList<>();

        for (CalculationResults result : this.calculationResultsRepository.findAllByGuid(guid)) {
            MathematicsResult mathematicsResult = new MathematicsResult();
            mathematicsResult.setResult(result.getResult());
            mathematicsResult.setAlias(result.getFormulaAlias());
            mathematicsResult.setCreateDate(result.getCreateDate());
            mathematicsResult.setGuid(result.getGuid());

            mathematicsResults.add(mathematicsResult);
        }

        return mathematicsResults;
    }
}
