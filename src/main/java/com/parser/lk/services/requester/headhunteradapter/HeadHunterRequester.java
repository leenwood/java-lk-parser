package com.parser.lk.services.requester.headhunteradapter;

import com.parser.lk.services.requester.RequesterInterface;
import com.parser.lk.services.requester.dto.VacanciesResponseInterface;
import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import com.parser.lk.services.vacanciesparser.dto.FilterParamInterface;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HeadHunterRequester implements RequesterInterface {
    private HeadHunterAdapterService headHunterAdapterService;
    private RestTemplate restTemplate;

    @Autowired
    public void prepare(HeadHunterAdapterService headHunterAdapterService) {
        this.headHunterAdapterService = headHunterAdapterService;
        this.restTemplate = new RestTemplate();
    }

    public VacanciesResponseInterface getVacancies(FilterParamInterface filter) {

        ResponseEntity<VacanciesResponse> response = this.restTemplate.exchange(
                this.headHunterAdapterService.generateUrlVacancies(),
                HttpMethod.GET,
                this.headHunterAdapterService.getConfiguration(),
                VacanciesResponse.class
        );

        return response.getBody();
    }
}
