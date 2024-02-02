package com.parser.lk.controller;


import com.parser.lk.services.applicationservice.ChangeStatusService;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final ChangeStatusService changeStatusService;

    private final VacanciesParser vacanciesParser;

    public TestController(ChangeStatusService changeStatusService, VacanciesParser vacanciesParser) {
        this.changeStatusService = changeStatusService;
        this.vacanciesParser = vacanciesParser;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vacancies vacancies(
            @RequestParam(name = "text") String text,
            @RequestParam(name = "onlyWithSalary") Boolean onlyWithSalary,
            @RequestParam(name = "period") Integer period
            ) {
        HeadHunterFiltersParam filters = new HeadHunterFiltersParam(
                text,
                onlyWithSalary,
                period,
                "1",
                "between1And3"
                );
        var response = this.vacanciesParser.ParseHeadHunterVacancies(filters);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/areas", produces = MediaType.APPLICATION_JSON_VALUE)
    public void areas() {
        List<Integer> areas = this.vacanciesParser.getAreaIdsFromHeadHunter();
        return;
    }
}
