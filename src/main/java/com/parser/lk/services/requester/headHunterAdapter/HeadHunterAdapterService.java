package com.parser.lk.services.requester.headHunterAdapter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class HeadHunterAdapterService {

    private String private_api_key = "APPLSC5PEPV2IAA90AHF4HCOR9476BR4PEOG6URII746V57IIJGKIUL1160QU9OU";

    private String base_url_address = "https://api.hh.ru/";

    public String generateUrlVacancies() {
        return this.base_url_address + "/vacancies";
    }

    public HttpEntity<String> getConfiguration() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.private_api_key);
        return new HttpEntity<>(headers);
    }

}
