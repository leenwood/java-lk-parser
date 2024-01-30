package com.parser.lk.services.vacanciesparser;

import com.parser.lk.services.requester.dto.VacanciesResponseInterface;
import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import com.parser.lk.services.vacanciesparser.dto.FilterParamInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class VacanciesParser {

    private final HeadHunterRequester headHunterRequester;

    @Autowired @Qualifier("defaultConversionService") ConversionService conversionService;


    @Autowired
    public VacanciesParser(HeadHunterRequester headHunterRequester) {
        this.headHunterRequester = headHunterRequester;
    }

    private void ParseHeadHunterVacancies(FilterParamInterface filter) {
        VacanciesResponseInterface vacancies = this.headHunterRequester.getVacancies(filter);
    }

}
