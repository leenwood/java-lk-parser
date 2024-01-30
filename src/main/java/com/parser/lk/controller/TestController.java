package com.parser.lk.controller;


import com.parser.lk.services.requester.VacanciesResponseInterface;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final VacanciesParser vacanciesParser;

    public TestController(VacanciesParser vacanciesParser) {
        this.vacanciesParser = vacanciesParser;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacanciesResponseInterface vacancies() {
        var filter = new HeadHunterFiltersParam();
        return null;
    }
}
