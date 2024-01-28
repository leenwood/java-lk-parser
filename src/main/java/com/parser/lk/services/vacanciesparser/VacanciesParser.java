package com.parser.lk.services.vacanciesparser;

import com.parser.lk.services.requester.RequesterEnum;
import com.parser.lk.services.requester.RequesterFactory;
import com.parser.lk.services.requester.RequesterInterface;
import com.parser.lk.services.requester.dto.VacanciesResponseInterface;
import com.parser.lk.services.vacanciesparser.dto.FilterParamInterface;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class VacanciesParser {

    private final RequesterFactory factory;

    @Autowired @Qualifier("defaultConversionService") ConversionService conversionService;


    @Autowired
    public VacanciesParser(RequesterFactory factory) {
        this.factory = factory;
    }

    public Vacancies ParseVacancies(FilterParamInterface filter) {

        if (filter.getFilterName().equals("HeadHunterFilter")) {
            return this.ParseHeadHunterVacancies(filter);
        }
        return null;
    }

    private Vacancies ParseHeadHunterVacancies(FilterParamInterface filter) {
        RequesterInterface requester = this.factory.getRequester(RequesterEnum.HEAD_HUNTER_REQUESTER);
        VacanciesResponseInterface vacancies = requester.getVacancies(filter);
        return null;
    }

}
