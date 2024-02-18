package com.parser.lk.repository;

import com.parser.lk.entity.Formula;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FormulaRepository extends CrudRepository<Formula, Long> {

    public Optional<Formula> findFirstByAlias(String alias);

}
