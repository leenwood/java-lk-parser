package com.parser.lk.services.requester.headhunteradapter;

import com.parser.lk.services.requester.headhunteradapter.dto.AreaResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;

@Service("HeadHunterRequesterBean")
public class HeadHunterRequester {
    private final RestTemplate restTemplate  = new RestTemplate();

    @Value("${application.headhunter.api.key}")
    private String private_api_key;

    private String base_url_address = "https://api.hh.ru/";

    @Value("${application.useragent.lk.users}")
    private String user_agent;

    @Value("${application.areas.url}")
    private String areasUrl;


    public VacanciesResponse getVacancies(HeadHunterFiltersParam filters) {
        this.restTemplate.getMessageConverters().addFirst(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<VacanciesResponse> response = this.restTemplate.exchange(
                this.generateUrlVacancies(filters),
                HttpMethod.GET,
                this.getConfiguration(),
                VacanciesResponse.class
        );
        return response.getBody();
    }

    public AreaResponse getAreasByDictionary() {

        return this.restTemplate.exchange(
                this.areasUrl,
                HttpMethod.GET,
                this.getConfiguration(),
                AreaResponse.class
        ).getBody();
    }


    private String generateUrlVacancies(HeadHunterFiltersParam filtersParam) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(this.base_url_address)
                .path("/vacancies");

        if (filtersParam.getOnlyWithSalary() != null)
            uri.queryParam("only_with_salary", filtersParam.getOnlyWithSalary());

        if (filtersParam.getText() != null) {
            uri.queryParam("text",  filtersParam.getText());
        }

        if (filtersParam.getPeriod() != null) uri.queryParam("period", filtersParam.getPeriod());

        if (filtersParam.getArea() != null) uri.queryParam("area", filtersParam.getArea());

        if (filtersParam.getExperience() != null) uri.queryParam("experience", filtersParam.getExperience());

        // Базовые методы которые нулл не могут быть
        uri
                .queryParam("page", filtersParam.getPage())
                .queryParam("per_page", filtersParam.getPerPage())
                .queryParam("no_magic", filtersParam.isNoMagic());
        return uri.build().toUriString();
    }

    private HttpEntity<String> getConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.private_api_key);
        headers.set("User-Agent", this.user_agent);
        headers.set("content-type", "application/json; charset=UTF-8");
        return new HttpEntity<>(headers);
    }
}
