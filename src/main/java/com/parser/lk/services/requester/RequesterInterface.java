package com.parser.lk.services.requester;

import com.parser.lk.services.requester.dto.VacanciesResponseInterface;
import com.parser.lk.services.vacanciesparser.dto.FilterParamInterface;

public interface RequesterInterface {

    public VacanciesResponseInterface getVacancies(FilterParamInterface filter);

}
