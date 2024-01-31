package com.parser.lk.controller;


import com.parser.lk.queue.OrderMessage;
import com.parser.lk.services.applicationservice.ChangeStatusService;
import com.parser.lk.services.requester.VacanciesResponseInterface;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
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
    public VacanciesResponseInterface vacancies() {
        OrderMessage message = new OrderMessage();
        message.setOrderId(1002L);
        message.setNextStatus("PARSING");
        message.setCurrentStatus("START_PARSING");
        this.changeStatusService.changeStatus(message);
        return null;
    }
}
