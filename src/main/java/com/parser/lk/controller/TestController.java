package com.parser.lk.controller;


import com.parser.lk.entity.CalculationResults;
import com.parser.lk.repository.CalculationResultsRepository;
import com.parser.lk.services.applicationservice.ChangeStatusService;
import com.parser.lk.services.applicationservice.applicationstatus.PostProcessingService;
import com.parser.lk.services.calculationservice.CalculationWorkflow;
import com.parser.lk.services.documentmanager.XlsxDocumentService;
import com.parser.lk.services.requester.centralbank.CentralBankRequester;
import com.parser.lk.services.telegrambotservice.TelegramNotificationService;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {

    private final ChangeStatusService changeStatusService;

    private final PostProcessingService postProcessingService;

    private final VacanciesParser vacanciesParser;

    private final XlsxDocumentService xlsxDocumentService;

    private final CentralBankRequester centralBankRequester;

    private final CalculationWorkflow calculationWorkflow;

    private final CalculationResultsRepository calculationResultsRepository;

    private final TelegramNotificationService telegramNotificationService;

    public TestController(
            ChangeStatusService changeStatusService,
            PostProcessingService postProcessingService,
            VacanciesParser vacanciesParser,
            XlsxDocumentService xlsxDocumentService,
            CentralBankRequester centralBankRequester,
            CalculationWorkflow calculationWorkflow,
            CalculationResultsRepository calculationResultsRepository,
            TelegramNotificationService telegramNotificationService
    ) {
        this.changeStatusService = changeStatusService;
        this.postProcessingService = postProcessingService;
        this.vacanciesParser = vacanciesParser;
        this.xlsxDocumentService = xlsxDocumentService;
        this.centralBankRequester = centralBankRequester;
        this.calculationWorkflow = calculationWorkflow;
        this.calculationResultsRepository = calculationResultsRepository;
        this.telegramNotificationService = telegramNotificationService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vacancies vacancies(
            @RequestParam(name = "text") String text,
            @RequestParam(name = "onlyWithSalary") Boolean onlyWithSalary,
            @RequestParam(name = "period") Integer period
            ) {

        List<Integer> areas = new ArrayList<>();
        areas.add(1620);
        areas.add(1624);
        areas.add(1);

        HeadHunterFiltersParam filters = new HeadHunterFiltersParam(
                text,
                onlyWithSalary,
                period,
                areas,
                "between1And3",
                null,
                null,
                null,
                null
                );
        var response = this.vacanciesParser.ParseHeadHunterVacancies(filters);
        return response;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/areas", produces = MediaType.APPLICATION_JSON_VALUE)
    public void areas() {
        List<Integer> areas = this.vacanciesParser.getAreaIdsFromHeadHunter();
        return;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public void test2() {
        this.postProcessingService.doProcess(1L);
        return;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCurrency", produces = MediaType.APPLICATION_JSON_VALUE)
    public double currency() throws Exception {
        return this.centralBankRequester.getCurrencyByAlias("USD");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test3", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test3(@RequestParam(name = "orderId") Long orderId) throws Exception {
        this.calculationWorkflow.baseWorkflow(orderId);
        return "21";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test4", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test4(@RequestParam(name = "orderId") String orderId) throws Exception {
        for (CalculationResults calculationResults : this.calculationResultsRepository.findAllByGuid(orderId)) {
            System.out.println(calculationResults.getResult());
            System.out.println(calculationResults.getFormulaAlias());
        }
        return "21";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test5", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test5(@RequestParam(name = "text") String text) throws Exception {
        this.telegramNotificationService.sendNotification(text, "https://www.youtube.com/watch?v=3_zofPNyLq4&list=RDUpD8Yycy9aM&index=4");
        return "21";
    }
}
