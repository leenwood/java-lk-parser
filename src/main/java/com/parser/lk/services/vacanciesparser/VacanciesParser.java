package com.parser.lk.services.vacanciesparser;

import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacanciesParser {

    private final HeadHunterRequester headHunterRequester;

    @Autowired
    public VacanciesParser(HeadHunterRequester headHunterRequester) {
        this.headHunterRequester = headHunterRequester;
    }

    public Vacancies ParseHeadHunterVacancies(HeadHunterFiltersParam filter) {
        VacanciesResponse vacancies = this.headHunterRequester.getVacancies(filter);
        System.out.println(1);
        return null;
    }

}
