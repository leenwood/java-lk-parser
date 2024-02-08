package com.parser.lk.repository;

import com.parser.lk.entity.Vacancy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {
}
