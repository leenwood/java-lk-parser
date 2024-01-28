package com.parser.lk.services.vacanciesparser.dto.vacancies;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parser.lk.services.requester.headhunteradapter.dto.AreaResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.EmploymentResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.SalaryResponse;

public class Vacancy {
    private String id;

    private String name;

    @JsonProperty("alternate_url")
    private String originUrl;

    private AreaResponse area;

    private SalaryResponse salary;

    private EmploymentResponse employment;
}
