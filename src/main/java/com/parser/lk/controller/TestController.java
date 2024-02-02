package com.parser.lk.controller;


import com.parser.lk.services.applicationservice.ChangeStatusService;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ChangeStatusService changeStatusService;

    private final VacanciesParser vacanciesParser;

    public TestController(ChangeStatusService changeStatusService, VacanciesParser vacanciesParser) {
        this.changeStatusService = changeStatusService;
        this.vacanciesParser = vacanciesParser;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vacancies vacancies() {
        HeadHunterFiltersParam filters = new HeadHunterFiltersParam(
                "php-программист",
                true,
                30,
                "1",
                "between1And3"
                );
        var response = this.vacanciesParser.ParseHeadHunterVacancies(filters);
        System.out.println("end");
        return null;
    }
}
