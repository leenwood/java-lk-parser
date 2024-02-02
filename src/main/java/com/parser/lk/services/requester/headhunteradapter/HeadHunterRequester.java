package com.parser.lk.services.requester.headhunteradapter;

import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service("HeadHunterRequesterBean")
public class HeadHunterRequester {
    private final RestTemplate restTemplate  = new RestTemplate();

    private String private_api_key = "APPLSC5PEPV2IAA90AHF4HCOR9476BR4PEOG6URII746V57IIJGKIUL1160QU9OU";

    private String base_url_address = "https://api.hh.ru/";


    public VacanciesResponse getVacancies(HeadHunterFiltersParam filters) {
        String value = this.generateUrlVacancies(filters);
        ResponseEntity<VacanciesResponse> response = this.restTemplate.exchange(
                this.generateUrlVacancies(filters),
                HttpMethod.GET,
                this.getConfiguration(),
                VacanciesResponse.class
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
            uri.queryParam("text", URLEncoder.encode("php-программист", StandardCharsets.UTF_8));
        }

        if (filtersParam.getPeriod() != null) uri.queryParam("period", filtersParam.getPeriod());

        if (filtersParam.getArea() != null) uri.queryParam("area", filtersParam.getArea());

        if (filtersParam.getExperience() != null) uri.queryParam("experience", filtersParam.getExperience());

        // Базовые методы которые нулл не могут быть
        uri
                .queryParam("page", filtersParam.getPage())
                .queryParam("per_page", filtersParam.getPerPage())
                .queryParam("no_magic", filtersParam.isNoMagic());

        return uri.toUriString();
    }

    private HttpEntity<String> getConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.private_api_key);
        headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0");
        headers.set("content-type", "application/json; charset=UTF-8");
        return new HttpEntity<>(headers);
    }
}
