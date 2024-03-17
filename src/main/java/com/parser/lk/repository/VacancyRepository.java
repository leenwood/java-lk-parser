package com.parser.lk.repository;

import com.parser.lk.entity.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends CrudRepository<Vacancy, Long> {

    public Vacancy findFirstByGuidAndProcessed(String guid, boolean processed);

    public Integer countByGuidAndProcessed(String guid, boolean processed);

    public Page<Vacancy> findAllByGuidAndProcessed(String guid, boolean processed, Pageable pageable);

    public Iterable<Vacancy> findAllByGuid(String guid);

}
