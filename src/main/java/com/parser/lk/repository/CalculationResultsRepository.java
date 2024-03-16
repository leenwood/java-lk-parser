package com.parser.lk.repository;

import com.parser.lk.entity.CalculationResults;
import org.springframework.data.repository.CrudRepository;

public interface CalculationResultsRepository extends CrudRepository<CalculationResults, Long> {

    public Iterable<CalculationResults> findAllByGuid(String guid);

}
