package com.parser.lk.repository;

import com.parser.lk.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {

    public Vacancy findOneByGuidAndProcessed(String guid, boolean processed);

    public Integer countByGuidAndProcessed(String guid, boolean processed);

}
