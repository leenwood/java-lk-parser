package com.parser.lk.services.calculationservice;


import com.parser.lk.entity.Formula;
import com.parser.lk.exception.NotFoundException;
import com.parser.lk.repository.FormulaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FormulaManager {

    private final FormulaRepository formulaRepository;

    public FormulaManager(FormulaRepository formulaRepository) {
        this.formulaRepository = formulaRepository;
    }


    public boolean createFormula(
            String alias,
            String description,
            String formula
    ) throws NotFoundException {

        Optional<Formula> formulaOptional = this.formulaRepository.findFirstByAlias(alias);
        if (formulaOptional.isPresent()) {
            throw new NotFoundException(String.format("Formula with alias (%s) is defined", alias));
        }



        return false;
    }

}
