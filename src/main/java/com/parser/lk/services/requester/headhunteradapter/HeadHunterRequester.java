package com.parser.lk.services.requester.headhunteradapter;

import com.parser.lk.services.requester.headhunteradapter.areacache.AreaCache;
import com.parser.lk.services.requester.headhunteradapter.dto.AreaResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.VacancyResponse;
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


    private final AreaCache areaCache;

    public HeadHunterRequester(AreaCache areaCache) {
        this.areaCache = areaCache;
    }


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

    public AreaResponse getAreaById(String id) {
        if (this.areaCache.isEmpty()) {
            AreaResponse response = this.getAreasByDictionary();
            this.areaCache.createCache(response);
            return this.restTemplate.exchange(
                    String.format("https://api.hh.ru/areas/%s", id),
                    HttpMethod.GET,
                    this.getConfiguration(),
                    AreaResponse.class
            ).getBody();
        } else {
            return this.areaCache.getById(id);
        }
    }

    public int getPagesVacanciesByFilter(HeadHunterFiltersParam filters) {
        return this.restTemplate.exchange(
                this.generateUrlVacancies(filters),
                HttpMethod.GET,
                this.getConfiguration(),
                VacanciesResponse.class
        ).getBody().getPages();
    }


    public VacancyResponse getVacancyById(String id) {
        this.restTemplate.getMessageConverters().addFirst(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<VacancyResponse> response = this.restTemplate.exchange(
                this.generateUrlVacancy(id),
                HttpMethod.GET,
                this.getConfiguration(),
                VacancyResponse.class
        );
        return response.getBody();
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

        if (!filtersParam.getArea().isEmpty()) uri.queryParam("area", filtersParam.getArea());

        if (filtersParam.getExperience() != null) uri.queryParam("experience", filtersParam.getExperience());

        if (filtersParam.getEmployment() != null) uri.queryParam("employment", filtersParam.getEmployment());

        if (filtersParam.getSchedule() != null) uri.queryParam("schedule", filtersParam.getSchedule());

        if (!filtersParam.getIndustries().isEmpty()) uri.queryParam("industry", filtersParam.getIndustries());

        if (!filtersParam.getVacancySearchFields().isEmpty()) {
            uri.queryParam("search_field", filtersParam.getVacancySearchFields());
        }

        // Базовые методы которые нулл не могут быть
        uri
                .queryParam("page", filtersParam.getPage())
                .queryParam("per_page", filtersParam.getPerPage())
                .queryParam("no_magic", filtersParam.isNoMagic());
        System.out.println(uri.build().toUriString());
        return uri.build().toUriString();
    }

    private String generateUrlVacancy(String id) {
        UriComponentsBuilder uri = UriComponentsBuilder
                .fromHttpUrl(this.base_url_address)
                .path(String.format("/vacancies/%s", id));
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
